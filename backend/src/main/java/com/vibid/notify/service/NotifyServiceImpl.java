package com.vibid.notify.service;

import com.vibid.board.domain.Board;
import com.vibid.board.repository.BoardRepository;
import com.vibid.exception.NotFoundException;
import com.vibid.member.domain.Member;
import com.vibid.member.repository.MemberRepository;
import com.vibid.notify.domain.Notify;
import com.vibid.notify.repository.NotifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotifyServiceImpl implements NotifyService {

  private final NotifyRepository notifyRepository;
  private final MemberRepository memberRepository;
  private final BoardRepository boardRepository;

  @Override
  public Notify save(Long memberId, Long boardId, String content) {
    return memberRepository.findById(memberId)
      .map(member ->
        boardRepository.findById(boardId)
          .map(board ->
            notifyRepository.save(
              Notify.builder()
                .member(member)
                .board(board)
                .content(content)
                .build()))
          .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다.",boardId))
      ).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다.",memberId));
  }

  @Override
  public Notify save(Member member, Board board, String content) {
    Notify notify = Notify.builder()
      .member(member)
      .board(board)
      .content(content)
      .build();
    return notifyRepository.save(notify);
  }

  @Override
  public Optional<Notify> read(Long notifyId) {
    return notifyRepository.findById(notifyId)
      .map(notify -> notify.read());
  }

  @Override
  public Boolean delete(Long notifyId) {
    return notifyRepository.findById(notifyId)
      .map(notify -> {
        notifyRepository.delete(notify);
        return true;}).isPresent();
  }

  @Override
  public void deleteAllByMemberId(Long memberId) {
    notifyRepository.deleteAllById(memberId);
  }

  @Override
  public List<Notify> findAllByMemberId(Long memberId) {
    return notifyRepository.findAllById(memberId);
  }
}
