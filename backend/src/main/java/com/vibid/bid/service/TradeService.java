package com.vibid.bid.service;

import com.vibid.bid.controller.dto.BuyerDto;
import com.vibid.bid.controller.dto.SellerDto;
import com.vibid.bid.domain.Trade;
import com.vibid.bid.domain.TradeType;

import java.util.List;

public interface TradeService {
  Boolean buyerSave(Long memberId, BuyerDto dto);

  Boolean sellerSave(Long memberId, SellerDto dto);

  Boolean submit(Long memberId, Long boardId);

  List<Trade> findAllByMemberId(Long memberId);

  List<Trade> findAllByMemberIdAndType(Long memberId, TradeType type);
}
