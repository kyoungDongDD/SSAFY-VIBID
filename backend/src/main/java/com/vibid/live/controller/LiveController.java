package com.vibid.live.controller;

import com.vibid.api.ApiResult;
import com.vibid.auth.jwt.domain.JwtAuthentication;
import com.vibid.bid.service.BidService;
import com.vibid.board.domain.Board;
import com.vibid.board.service.BoardService;
import com.vibid.exception.NotFoundException;
import com.vibid.live.controller.dto.BoardForSessionDto;
import com.vibid.live.controller.dto.RequestSessionDto;
import com.vibid.live.controller.dto.ResponseSessionDto;
import com.vibid.live.service.LiveService;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/live")
@RequiredArgsConstructor
@Slf4j
public class LiveController {

  private final LiveService liveService;
  private final BidService bidService;

  @PostMapping("{id}")
  public ApiResult<?> request(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable Long id) {
    try {
      String token = liveService.joinLive(authentication.getId(), authentication.getNickname(), id);
      return ApiResult.OK(token);
    } catch (OpenViduJavaClientException e) {
      return ApiResult.ERROR(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (OpenViduHttpException e) {
      return ApiResult.ERROR(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (IllegalAccessException e) {
      return ApiResult.ERROR(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/start/{id}")
  public ApiResult<?> liveStart(@AuthenticationPrincipal JwtAuthentication authentication,
                                @PathVariable Long id) {
    return ApiResult.OK(liveService.liveOpen(authentication.getId(), id));
  }

  /**
   * 사용자가 live session에서 떠나는 경우
   *  - 사용자 : Publisher   : live session 종료 및 모든 Subscriber 추방
   *  - 사용자 : Subscriber  : live session에서 혼자만 나가기
   * @param sessionDto
   * @return
   */
  @DeleteMapping
  public ApiResult<?> leave(@AuthenticationPrincipal JwtAuthentication authentication,
                               @RequestBody RequestSessionDto sessionDto) {
    if(sessionDto.getBoardId() == null)
      throw new IllegalArgumentException();

    try {
      liveService.closeLive(authentication.getId(), sessionDto.getBoardId(), sessionDto.getToken());
      return ApiResult.OK(null);
    } catch (OpenViduHttpException e) {
      return ApiResult.ERROR(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (OpenViduJavaClientException e) {
      return ApiResult.ERROR(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }



}
