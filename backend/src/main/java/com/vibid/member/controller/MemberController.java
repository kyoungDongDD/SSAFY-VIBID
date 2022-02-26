package com.vibid.member.controller;

import com.vibid.api.ApiResult;
import com.vibid.auth.jwt.domain.JwtAuthentication;
import com.vibid.auth.service.AuthService;
import com.vibid.files.service.ImageService;
import com.vibid.exception.NotFoundException;
import com.vibid.member.controller.dto.MemberDetailDto;
import com.vibid.member.controller.dto.MemberDto;
import com.vibid.member.controller.dto.PasswordUpdateDto;
import com.vibid.member.controller.dto.RequestJoinDto;
import com.vibid.member.domain.Email;
import com.vibid.member.domain.Member;
import com.vibid.member.domain.Password;
import com.vibid.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class MemberController {

  private final MemberService memberService;

  private final ImageService imageService;

  private final AuthService authService;

  @PostMapping
  public ApiResult<Void> join(@RequestBody RequestJoinDto joinDto) {
    memberService.join(joinDto)
      .map(member -> {
        authService.createEmailToken(member);
        return member;
      });
    return ApiResult.OK(null);
  }

  @DeleteMapping
  public ApiResult<Void> withdrawal(@AuthenticationPrincipal JwtAuthentication authentication) {
    memberService.withdrawal(authentication.getId());
    SecurityContextHolder.clearContext();
    return ApiResult.OK(null);
  }

  @PatchMapping("/nickname")
  public ApiResult<Void> nicknameUpdate(@AuthenticationPrincipal JwtAuthentication authentication,
                                        @RequestBody Map<String, String> request) {
    memberService.updateNickname(authentication.getId(), request.get("nickname"))
      .orElseThrow(
        () -> new NotFoundException(Member.class, authentication.getId())
      );
    return ApiResult.OK(null);
  }

  @PatchMapping("/profileImage")
  public ApiResult<?> profileImageUpdate(@AuthenticationPrincipal JwtAuthentication authentication,
                                         @RequestParam MultipartFile file) {

    try {
      String fileName = imageService.store(file);
      memberService.updateProfileUrl(authentication.getId(), fileName);
      return ApiResult.OK(fileName);
    } catch (IOException e) {
      return ApiResult.ERROR(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/password")
  public ApiResult<Void> passwordUpdate(@AuthenticationPrincipal JwtAuthentication authentication,
                                        @RequestBody Map<String, String> passwords) {
    memberService.findById(authentication.getId())
        .map( member ->
          memberService.updatePassword(member, Password.of(passwords.get("password"), passwords.get("passwordConfirm")))
        ).orElseThrow(
        () -> new NotFoundException(Member.class, authentication.getId())
      );

    return ApiResult.OK(null);
  }

  @GetMapping
  public ApiResult<MemberDetailDto> findById(@AuthenticationPrincipal JwtAuthentication authentication, @RequestParam(required = false) Long id) {
    return ApiResult.OK(
      memberService.findById((id == null) ? authentication.getId() : id)
        .map(
          member -> {
            if (!member.isWithdrawal())
              return new MemberDetailDto(member);
            return null;
          })
        .orElseThrow(() -> new NotFoundException(Member.class, authentication.getId()))
    );
  }

  @GetMapping("/changePassword")
  public ApiResult<Void> requestChangePassword(@RequestParam String address) {
    authService.createPasswordToken(memberService.findByEmail(Email.from(address))
      .orElseThrow(() -> new NotFoundException(Member.class, address)));
    return ApiResult.OK(null);
  }

  @GetMapping("/exist")
  public ApiResult<Boolean> checkEmail(@RequestParam String id) {
    log.debug("이메일 중복 확인 : " + id);
    return ApiResult.OK(memberService.checkExistEmail(Email.from(id)));
  }

  @GetMapping("/me")
  public ApiResult<MemberDto> me(@AuthenticationPrincipal JwtAuthentication authentication) {
    return ApiResult.OK(memberService.findById(authentication.getId())
      .map(member -> new MemberDto(member))
      .orElseThrow(() -> new NotFoundException(Member.class, authentication.getId())));
  }
}
