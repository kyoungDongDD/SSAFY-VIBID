package com.vibid.bid.service;

import com.vibid.bid.controller.dto.BuyerDto;
import com.vibid.bid.controller.dto.SellerDto;
import com.vibid.bid.domain.Trade;
import com.vibid.bid.domain.TradeInfo;
import com.vibid.bid.domain.TradeType;
import com.vibid.bid.repository.TradeRepository;
import com.vibid.board.repository.BoardRepository;
import com.vibid.notify.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeServiceImpl implements TradeService {

  private final BoardRepository boardRepository;
  private final TradeRepository tradeRepository;
  private final NotifyService notifyService;

  @Override
  public Boolean buyerSave(Long memberId, BuyerDto dto) {

    return tradeRepository.findByBoardId(dto.getBoardId(), TradeType.BUY)
      .filter(trade -> trade.getMember().getId() == memberId)
      .map(trade -> {
        trade.setTradeInfo(
          TradeInfo.buyer(
            dto.getName(),
            dto.getAddress(),
            dto.getZipcode(),
            dto.getContact()));
        return trade;
      })
      .map(dummy ->
        tradeRepository.findByBoardId(dto.getBoardId(), TradeType.SELL)
          .map(trade ->
            notifyService.save(trade.getMember(), trade.getBoard(),
              "[" + trade.getBoard().getTitle() + "] 의 구매자 정보가 등록되었습니다."))
      )
      .isPresent();
  }

  @Override
  public Boolean sellerSave(Long memberId, SellerDto dto) {
    return tradeRepository.findByBoardId(dto.getBoardId(), TradeType.SELL)
      .filter(trade -> trade.getMember().getId() == memberId)
      .map(trade -> {
        TradeInfo seller = TradeInfo.seller(
          dto.getName(),
          dto.getAccount(),
          dto.getBank(),
          dto.getContact());
        trade.setTradeInfo(seller);
        seller.setTrade(trade);
        return trade;
      })
      .map(dummy ->
        tradeRepository.findByBoardId(dto.getBoardId(), TradeType.BUY)
          .map(trade ->
            notifyService.save(trade.getMember(), trade.getBoard(),
              "[" + trade.getBoard().getTitle() + "] 의 판매자 정보가 등록되었습니다."))
      )
      .isPresent();
  }

  @Override
  public Boolean submit(Long memberId, Long boardId) {
    return boardRepository.findById(boardId)
      .map(b -> {
        boolean isAllConfirm = true;
        Iterator<Trade> iterator = b.getTrades().iterator();
        while (iterator.hasNext()) {
          Trade trade = iterator.next();
          if (trade.getMember().getId() == memberId) {
            if(trade.isConfirm())
              return null;
            trade.setConfirm();
          }
          isAllConfirm &= trade.isConfirm();
        }
        if (isAllConfirm) {
          iterator = b.getTrades().iterator();
          while (iterator.hasNext()) {
            Trade trade = iterator.next();
            trade.setEnd();
            trade.clearInfo();
          }
        }
        return true;
      }).isPresent();
  }

  @Override
  public List<Trade> findAllByMemberId(Long memberId) {
    return tradeRepository.findAllByMemberId(memberId);
  }

  @Override
  public List<Trade> findAllByMemberIdAndType(Long memberId, TradeType type) {
    return tradeRepository.findAllByMemberIdAndType(memberId,type);
  }
}
