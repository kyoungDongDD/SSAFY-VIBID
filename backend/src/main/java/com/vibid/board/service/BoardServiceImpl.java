package com.vibid.board.service;

import com.vibid.board.controller.dto.OrderType;
import com.vibid.board.controller.dto.PostPreviewDto;
import com.vibid.board.controller.dto.PostingRequestDto;
import com.vibid.board.controller.dto.SortType;
import com.vibid.board.domain.*;
import com.vibid.board.repository.*;

import com.vibid.exception.NotFoundException;
import com.vibid.live.service.LiveService;
import com.vibid.member.domain.Member;
import com.vibid.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;

  private final MemberService memberService;

  private final LiveService liveService;

  private final HashtagRepository hashtagRepository;

  private final WishRepository wishRepository;

  @Override
  public Board write(Long id, PostingRequestDto postingRequestDto) {
    Member member = memberService.findById(id)
      .orElseThrow(() -> new NotFoundException(Member.class, id));

    Board board = Board.builder() // 빌더로 입력
      .title(postingRequestDto.getTitle())
      .content(postingRequestDto.getContent())
      .thumbnailImageUrl(postingRequestDto.getThumbnailImageUrl())
      .build();

    BidInfo bidInfo = BidInfo.builder()
      .startingPrice(postingRequestDto.getStartingPrice())
      .bidding(postingRequestDto.getBidding())
      .bidStartTime(postingRequestDto.getBidStartTime())
      .build();

    board.setBidInfo(bidInfo);
    board.setMember(member);

    List<Hashtag> hashtags = findAndSaveHashtags(postingRequestDto.getTags());
    hashtags.forEach(
      hashtag -> {
        BoardHashtag boardHashtag = BoardHashtag.builder()
          .board(board)
          .hashtag(hashtag)
          .build();
        board.getBoardHashtags().add(boardHashtag);
      }
    );

    postingRequestDto.getContentImageUrl().forEach(
      image -> {
        ContentImage contentImage = ContentImage.from(image);
        contentImage.setBoard(board);
      }
    );

    return boardRepository.save(board);
  }

  @Override
  public boolean update(Long memberId, Long boardId, PostingRequestDto postingDto) {
    return boardRepository.findById(boardId)
      .filter(board -> board.isAuthor(memberId))
      .map(board -> board.update(postingDto,findAndSaveHashtags(postingDto.getTags())))
      .isPresent();
  }

  @Override
  public boolean delete(Long memberId, Long boardId) {    //보드와 비드 정보 삭제

    return boardRepository.findById(boardId)
      .filter(board -> board.isAuthor(memberId))
      .map(board -> {
        boardRepository.delete(board);
        return true;
      }).isPresent();
  }

  @Override
  public boolean liftUp(Long memberId, Long boardId) {
    return boardRepository.findById(boardId)
      .filter(board -> board.isAuthor(memberId))
      .filter(board -> !board.getIsLiftUp())
      .map(board -> {
        board.lifeUp();
        return board;
      }).isPresent();
  }


  @Override
  public Optional<Board> findById(Long boardId) {
    return boardRepository.findById(boardId)
      .map(board -> {
        board.addViewCount();
        return board;
      });
  }

  @Override
  public List<PostPreviewDto> findAllByCondition(List<String> tags, boolean isLive, SortType sort, OrderType order, int offset, int limit) {
    return boardRepository.findAllByCondition(tags, isLive, sort, order, (offset - 1) * limit, limit).stream()
      .map(board -> PostPreviewDto.from(board))
      .collect(Collectors.toList());
  }

  @Override
  public List<PostPreviewDto> findAllByPopularityLive(OrderType orderType, int limit) {
    List<Long> findBoardId = liveService.popularityList(orderType, limit);
    List<PostPreviewDto> results = new ArrayList<>();
    findBoardId.forEach(id -> {
      results.add(
        boardRepository.findById(id)
          .map(board -> PostPreviewDto.from(board))
          .orElseThrow(() -> new NotFoundException(Board.class, id))
      );
    });
    return results;
  }

  @Override
  public boolean wish(Member member, Long boardId) {
    return boardRepository.findById(boardId)
      .map(board ->
        wishRepository.findByBoardAndMember(board,member)
          .map(wish -> {
            wishRepository.delete(wish);
            board.wishOff();
            return null;
          })
          .orElseGet(() -> {
            wishRepository.save(Wish.from(member, board));
            board.wishOn();
            return true;
          })
      ).isPresent();
  }

  private List<Hashtag> findAndSaveHashtags(List<String> hashtags) {
    List<Hashtag> results = new ArrayList<>();
    hashtags.forEach(
      tag ->
        results.add(
          hashtagRepository.findByName(tag)
            .orElseGet(() ->
              hashtagRepository.save(Hashtag.builder()
                .name(tag)
                .build()
              )
            )
        )
    );
    return results;
  }
}
