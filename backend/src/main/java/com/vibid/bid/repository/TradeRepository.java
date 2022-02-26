package com.vibid.bid.repository;

import com.vibid.bid.domain.Trade;
import com.vibid.bid.domain.TradeType;

import java.util.List;
import java.util.Optional;

public interface TradeRepository {

  Trade save(Trade trade);

  List<Trade> findAllByBoardId(Long boardId);

  Optional<Trade> findByBoardId(Long boardId, TradeType type);

  List<Trade> findAllByMemberId(Long memberId);

  List<Trade> findAllByMemberIdAndType(Long memberId, TradeType type);

}
