package com.vibid.board.repository;

import com.vibid.board.domain.Board;
import com.vibid.board.domain.Wish;
import com.vibid.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface WishRepository {

  Optional<Wish> findByBoardAndMember(Board board, Member member);

  void delete(Wish wish);

  Wish save(Wish wish);

  // TODO
  List<Member> findAllMemberByBoard(Board board);
}
