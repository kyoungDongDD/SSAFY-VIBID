package com.vibid.bid.repository;

import com.vibid.bid.domain.Bid;

import java.util.List;

public interface BidRepository {
  List<Bid> findByIdMaxPrice(Long boardId);
  Bid save(Bid bid);
  List<Bid> findByBoardId(Long boardId);
  List<Bid> findByMemberId(Long memberId);
  List<Bid> findAll();
}
