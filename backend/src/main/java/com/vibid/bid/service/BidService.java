package com.vibid.bid.service;

import com.vibid.bid.controller.dto.EndingBiddingDto;
import com.vibid.bid.domain.Bid;
import com.vibid.board.domain.Board;
import com.vibid.member.domain.Member;

import java.util.Optional;

public interface BidService {

  Optional<Bid> bidding(long price, Member member, Board board);

  void createSession(Long boardId);

  Boolean openBidding(Long memberId, Long boardId);

  Boolean isBiddingStart(Long boardId);

  EndingBiddingDto enddingBid(Long memberId, Long boardId);
}
