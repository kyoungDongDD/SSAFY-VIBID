package com.vibid.board.repository;

import com.vibid.board.controller.dto.OrderType;
import com.vibid.board.controller.dto.SortType;
import com.vibid.board.domain.Board;
import com.vibid.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

  Board save(Board board);

  List<Board> findAllByMemberId(Long id, int offset, int limit);

  List<Board> findWishAllByMember(Member member, int offset, int limit);

  List<Board> findBuyAllByMember(Member member);

  List<Board> findSellAllByMember(Member member);

  void delete(Board board);

  List<Board> findAllByCondition(List<String> tags, boolean isLive, SortType sort, OrderType order, int offset, int limit);

  Optional<Board> findById(Long boardId);

}
