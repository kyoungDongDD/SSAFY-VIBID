package com.vibid.board.service;

import com.vibid.board.controller.dto.OrderType;
import com.vibid.board.controller.dto.PostPreviewDto;
import com.vibid.board.controller.dto.PostingRequestDto;
import com.vibid.board.controller.dto.SortType;
import com.vibid.board.domain.Board;
import com.vibid.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface BoardService {

   Board write(Long id, PostingRequestDto postingRequestDto);

   boolean delete(Long memberId, Long boardId);

   boolean update(Long memberId, Long boardId, PostingRequestDto postingRequestDto);

  boolean liftUp(Long memberId, Long boardId);

  Optional<Board> findById(Long boardId);

  List<PostPreviewDto> findAllByCondition(List<String> tags, boolean isLive, SortType sort, OrderType order, int offset, int limit);

  List<PostPreviewDto> findAllByPopularityLive(OrderType orderType, int limit);

  boolean wish(Member memberId, Long boardId);
}
