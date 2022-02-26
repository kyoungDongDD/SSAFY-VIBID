package com.vibid.auth.controller;

import com.vibid.api.ApiResult;
import com.vibid.auth.controller.dto.LoginRequestDto;
import com.vibid.auth.controller.dto.LoginResponseDto;
import com.vibid.auth.jwt.domain.AuthenticationResult;
import com.vibid.auth.jwt.domain.JwtAuthenticationToken;
import com.vibid.auth.service.AuthService;
import com.vibid.member.controller.dto.PasswordUpdateDto;
import com.vibid.member.domain.Member;
import com.vibid.member.domain.Password;
import com.vibid.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final AuthService authService;
  private final MemberService memberService;

  @Value("${vibid.address}")
  private String address;

  @PostMapping
  public ApiResult<LoginResponseDto> authenticate(HttpServletResponse response,
                           @RequestBody LoginRequestDto loginDto) throws IOException {

    JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(loginDto.getId(), loginDto.getPassword());

    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return ApiResult.OK(new LoginResponseDto((AuthenticationResult) authentication.getDetails()));
  }

  @PostMapping("/postman")
  public ApiResult<LoginResponseDto> postmanAuthenticate(HttpServletResponse response,
                           @RequestBody LoginRequestDto loginDto) throws IOException {

    JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(loginDto.getId(), loginDto.getPassword());

    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    AuthenticationResult details = (AuthenticationResult) authentication.getDetails();
    Member member = details.getMember();

    return ApiResult.OK(new LoginResponseDto(details.getApiToken(), member));
  }

  @GetMapping("/confirm-email")
  public ResponseEntity<?> authenticateEmail(@RequestParam String token) throws URISyntaxException {
    authService.confirmEmail(token);
    // TODO 인증 실패시 어떻게 할 것인지 회의
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(new URI("https://i6b207.p.ssafy.io"));
    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
  }

  @PatchMapping("/password")
  public ApiResult<Void> passwordUpdate(@RequestBody PasswordUpdateDto updateDto) {
    authService.confirmPassword(updateDto.getToken())
      .map(member ->
        memberService.updatePassword(member, Password.of(updateDto.getPassword(), updateDto.getPasswordConfirm()))
      );
    return ApiResult.OK(null);
  }

}
