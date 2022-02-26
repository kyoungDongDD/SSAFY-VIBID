package com.vibid.bid.controller;

import com.vibid.api.ApiResult;
import com.vibid.auth.jwt.domain.JwtAuthentication;
import com.vibid.bid.controller.dto.*;
import com.vibid.bid.domain.Bid;
import com.vibid.bid.service.BidService;
import com.vibid.bid.service.TradeService;
import com.vibid.board.domain.Board;
import com.vibid.board.service.BoardService;
import com.vibid.exception.NotFoundException;
import com.vibid.live.service.LiveService;
import com.vibid.member.domain.Member;
import com.vibid.member.service.MemberService;
import com.vibid.notify.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BidController {

  private final SimpMessagingTemplate template;

  private final BidService bidService;

  private final LiveService liveService;

  private final MemberService memberService;

  private final BoardService boardService;

  private final TradeService tradeService;

  private final NotifyService notifyService;

  @MessageMapping("/bid/{id}")
  public void bidding(@DestinationVariable("id") Long id, RequestBiddingDto requestDto) throws IllegalAccessException {

    // 구독 URL
    StringBuilder destination = new StringBuilder();
    destination.append("/sub/bid/").append(id);

    // TODO Member가 없는 경우 검증
    Member member = memberService.findById(requestDto.getId())
      .orElseThrow(() -> new NotFoundException(Member.class, requestDto.getId()));

    // 유효한 Board id인지 체크
    Board board = boardService.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

    // 입찰자가 판매자인지 체크
    if(board.isAuthor(member.getId()) == true) {
      throw new IllegalAccessException("판매자는 입찰할 수 없습니다.");
    }

    if(bidService.isBiddingStart(id) == false) {
      throw new IllegalAccessException("입찰이 시작되지 않았습니다.");
    }

    template.convertAndSend(destination.toString(),
      ApiResult.OK(bidService.bidding(requestDto.getPayment(), member, board)
        .map(bid -> ResponseBiddingDto.pass(member.getNickname(), bid.getPrice()))
        .orElseGet(() -> ResponseBiddingDto.fail())));
  }

  @MessageMapping("/bid/end/{id}")
  public void winTheBid(@DestinationVariable("id") Long id, RequestBidEndDto requestDto) throws IllegalAccessException {
    // 구독 URL
    StringBuilder destination = new StringBuilder();
    destination.append("/sub/bid/").append(id);

    template.convertAndSend(destination.toString(),
      ApiResult.OK( bidService.enddingBid(requestDto.getMemberId(), id)));
  }

  @PutMapping("/api/bid/start/{id}")
  public ApiResult<?> startBid(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable Long id) {
    if(!liveService.isOpened(id))
      return ApiResult.ERROR("아직 경매가 시작되지 않았습니다.", HttpStatus.BAD_REQUEST);
    return ApiResult.OK(bidService.openBidding(authentication.getId(), id));
  }

  @PostMapping("/api/bid/buyerInfo")
  public ApiResult<?> setBuyerInfo(@AuthenticationPrincipal JwtAuthentication authentication,
                               @RequestBody BuyerDto dto) {
    return ApiResult.OK( tradeService.buyerSave(authentication.getId(), dto));
  }

  @PostMapping("/api/bid/sellerInfo")
  public ApiResult<?> setSellerInfo(@AuthenticationPrincipal JwtAuthentication authentication,
                               @RequestBody SellerDto dto) {
    return ApiResult.OK(tradeService.sellerSave(authentication.getId(), dto));
  }


}
