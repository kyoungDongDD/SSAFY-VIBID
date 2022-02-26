package com.vibid.bid.service;

import com.vibid.bid.controller.dto.EndingBiddingDto;
import com.vibid.bid.domain.Bid;
import com.vibid.bid.domain.Trade;
import com.vibid.bid.domain.TradeType;
import com.vibid.bid.repository.BidRepository;
import com.vibid.bid.repository.TradeRepository;
import com.vibid.board.domain.Board;
import com.vibid.board.repository.BoardRepository;
import com.vibid.board.repository.WishRepository;
import com.vibid.exception.NotFoundException;
import com.vibid.member.domain.Member;
import com.vibid.member.repository.MemberRepository;
import com.vibid.notify.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BidServiceImpl implements BidService {

  private final BidRepository bidRepository;
  private final BoardRepository boardRepository;
  private final TradeRepository tradeRepository;
  private final MemberRepository memberRepository;
  private final WishRepository wishRepository;
  private final NotifyService notifyService;

  private static Map<Long, Boolean> isBiddingOpened = new ConcurrentHashMap<>();

  @Override
  @Transactional
  public Optional<Bid> bidding(long price, Member member, Board board) {

    List<Bid> maxPrice = bidRepository.findByIdMaxPrice(board.getId());

    Bid bid = null;
    if (maxPrice.isEmpty() || maxPrice.get(0).getPrice() < price) {
      bid = Bid.builder()
        .price(price)
        .build();

      bid.setMember(member);
      bid.setBoard(board);

      bidRepository.save(bid);
    }
    return Optional.ofNullable(bid);
  }

  @Override
  public void createSession(Long boardId) {
    isBiddingOpened.put(boardId, false);
  }

  @Override
  public Boolean openBidding(Long memberId, Long boardId) {
    if(isBiddingOpened.containsKey(boardId)) {
      isBiddingOpened.replace(boardId, true);
      return true;
    }
    return false;
  }

  @Override
  public Boolean isBiddingStart(Long boardId) {
    if(isBiddingOpened.containsKey(boardId))
      return isBiddingOpened.get(boardId);
    return false;
  }

  @Override
  @Transactional
  public EndingBiddingDto enddingBid(Long memberId, Long boardId) {
    Member member = memberRepository.findById(memberId)
      .orElseThrow(() -> new NotFoundException(Member.class, memberId));

    Board board = boardRepository.findById(boardId)
      .filter(b -> b.isAuthor(member.getId()))
      .orElseThrow(() -> new NotFoundException(Board.class, boardId));

    List<Bid> maxPrice = bidRepository.findByIdMaxPrice(board.getId());

    return Optional.ofNullable(maxPrice.isEmpty() ? null : maxPrice.get(0))
      .filter(bid -> bid.getRegistTime().plusSeconds(30).isBefore(LocalDateTime.now()))
      .map(bid -> {
        wishRepository.findAllMemberByBoard(board).forEach(
          forMem -> notifyService.save(forMem,board,"["+board.getTitle()+"] 경매가 종료됐습니다"));

        Trade buyer = Trade.from(bid.getMember(), board, bid.getPrice(), TradeType.BUY);
        buyer.setBoard(board);
        tradeRepository.save(buyer);

        Trade seller = Trade.from(board.getMember(), board, bid.getPrice(), TradeType.SELL);
        seller.setBoard(board);
        tradeRepository.save(seller);

        board.getBidInfo().setEndingPrice(bid.getPrice());
        board.liveOff();
        return EndingBiddingDto.pass(bid.getMember().getId(), board.getMember().getId(), bid.getPrice());
      }).orElseGet(() -> EndingBiddingDto.fail());
  }
}
