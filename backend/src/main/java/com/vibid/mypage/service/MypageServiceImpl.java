package com.vibid.mypage.service;

import com.vibid.board.domain.Board;
import com.vibid.board.repository.BoardRepository;
import com.vibid.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MypageServiceImpl implements MypageService {

  private final BoardRepository boardRepository;

  @Override
  public List<Board> getMyWishes(Member member, int page) {
    return boardRepository.findWishAllByMember(member, (page-1)*10, 10);
  }

  @Override
  public List<Board> getGoods(Long memberId, int page) {
    return boardRepository.findAllByMemberId(memberId, (page-1)*10, 10);
  }
}
