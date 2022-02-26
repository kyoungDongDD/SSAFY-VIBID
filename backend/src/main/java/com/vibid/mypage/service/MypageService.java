package com.vibid.mypage.service;

import com.vibid.board.domain.Board;
import com.vibid.member.domain.Member;

import java.util.List;

public interface MypageService {

  List<Board> getMyWishes(Member member, int page);

  List<Board> getGoods(Long memberId, int page);

}
