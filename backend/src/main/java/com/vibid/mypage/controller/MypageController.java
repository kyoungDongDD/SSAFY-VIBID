package com.vibid.mypage.controller;

import com.vibid.api.ApiResult;
import com.vibid.auth.jwt.domain.JwtAuthentication;
import com.vibid.bid.domain.TradeType;
import com.vibid.bid.service.TradeService;
import com.vibid.board.controller.dto.PostPreviewDto;
import com.vibid.exception.NotFoundException;
import com.vibid.member.domain.Member;
import com.vibid.member.service.MemberService;
import com.vibid.mypage.controller.dto.TradeInfoDto;
import com.vibid.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageController {

  private final MypageService mypageService;
  private final MemberService memberService;
  private final TradeService tradeService;

  @GetMapping("/wish")
  public ApiResult<List<PostPreviewDto>> wishes(@AuthenticationPrincipal JwtAuthentication authentication,
                                                @RequestParam(required = false, defaultValue = "1") int page) {

    return ApiResult.OK(mypageService.getMyWishes(
        memberService.findById(authentication.getId())
          .orElseThrow(() -> new NotFoundException(Member.class, authentication.getId())),
        page).stream()
      .map(board -> PostPreviewDto.from(board))
      .collect(Collectors.toList()));
  }

  @GetMapping("/goods")
  public ApiResult<List<PostPreviewDto>> sells(@AuthenticationPrincipal JwtAuthentication authentication,
                                               @RequestParam(required = false, defaultValue = "1") int page) {
    return ApiResult.OK(mypageService.getGoods(authentication.getId(), page).stream()
      .map(board -> PostPreviewDto.from(board))
      .collect(Collectors.toList()));
  }

  @GetMapping("/trade")
  public ApiResult<List<TradeInfoDto>> tradeInfos(@AuthenticationPrincipal JwtAuthentication authentication) {
    Map<Long, TradeInfoDto> dtos = new HashMap<>();
    List<TradeInfoDto> result = new ArrayList<>();
    tradeService.findAllByMemberId(authentication.getId()).forEach(
      trade -> {
        TradeInfoDto dto = TradeInfoDto.of(trade.getBoard().getId(), trade.getBoard().getTitle(), trade.getPrice());
        if (dtos.putIfAbsent(trade.getBoard().getId(), dto) == null) {
          result.add(dto);
        }

        if (trade.getTradeType() == TradeType.BUY)
          dto.setBuyer(trade.getMember().getId(), trade.getTradeInfo());
        else
          dto.setSeller(trade.getMember().getId(), trade.getTradeInfo());
      }
    );
    return ApiResult.OK(result);
  }

  @GetMapping("/trade/sell")
  public ApiResult<List<TradeInfoDto>> getMySells(@AuthenticationPrincipal JwtAuthentication authentication) {
    Map<Long, TradeInfoDto> dtos = new HashMap<>();
    List<TradeInfoDto> result = new ArrayList<>();
    tradeService.findAllByMemberIdAndType(authentication.getId(),TradeType.SELL).forEach(
      trade -> {
        TradeInfoDto dto = TradeInfoDto.of(trade.getBoard().getId(), trade.getBoard().getTitle(), trade.getPrice());
        if (dtos.putIfAbsent(trade.getBoard().getId(), dto) == null) {
          result.add(dto);
        }

        if (trade.getTradeType() == TradeType.BUY)
          dto.setBuyer(trade.getMember().getId(), trade.getTradeInfo());
        else
          dto.setSeller(trade.getMember().getId(), trade.getTradeInfo());
      }
    );
    return ApiResult.OK(result);
  }

  @GetMapping("/trade/buy")
  public ApiResult<List<TradeInfoDto>> getMyBuys(@AuthenticationPrincipal JwtAuthentication authentication) {
    Map<Long, TradeInfoDto> dtos = new HashMap<>();
    List<TradeInfoDto> result = new ArrayList<>();
    tradeService.findAllByMemberIdAndType(authentication.getId(),TradeType.BUY).forEach(
      trade -> {
        TradeInfoDto dto = TradeInfoDto.of(trade.getBoard().getId(), trade.getBoard().getTitle(), trade.getPrice());
        if (dtos.putIfAbsent(trade.getBoard().getId(), dto) == null) {
          result.add(dto);
        }

        if (trade.getTradeType() == TradeType.BUY)
          dto.setBuyer(trade.getMember().getId(), trade.getTradeInfo());
        else
          dto.setSeller(trade.getMember().getId(), trade.getTradeInfo());
      }
    );
    return ApiResult.OK(result);
  }

  @PostMapping("/trade/submit/{id}")
  public ApiResult<Boolean> submit(@AuthenticationPrincipal JwtAuthentication authentication,
                                   @PathVariable Long id) {
    return ApiResult.OK(tradeService.submit(authentication.getId(), id));
  }

}
