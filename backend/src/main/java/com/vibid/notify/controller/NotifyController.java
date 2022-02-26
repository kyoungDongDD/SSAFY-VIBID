package com.vibid.notify.controller;

import com.vibid.api.ApiResult;
import com.vibid.auth.jwt.domain.JwtAuthentication;
import com.vibid.notify.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotifyController {

  private final NotifyService notifyService;

  @GetMapping
  public ApiResult<List<NotifyDto>> notifies(@AuthenticationPrincipal JwtAuthentication authentication) {
      return ApiResult.OK(
        notifyService.findAllByMemberId(authentication.getId()).stream()
        .map(notify -> NotifyDto.of(notify)).collect(Collectors.toList()));
  }

  @PatchMapping("{id}")
  public ApiResult<Boolean> read(@AuthenticationPrincipal JwtAuthentication authentication,
                           @PathVariable Long id) {
    return ApiResult.OK(notifyService.read(id).isPresent());
  }

  @DeleteMapping("{id}")
  public ApiResult<Boolean> delete(@AuthenticationPrincipal JwtAuthentication authentication,
                                   @PathVariable Long id) {
    return ApiResult.OK(notifyService.delete(id));
  }

  @DeleteMapping
  public ApiResult<?> deleteAll(@AuthenticationPrincipal JwtAuthentication authentication) {
    notifyService.deleteAllByMemberId(authentication.getId());
    return ApiResult.OK(null);
  }
}
