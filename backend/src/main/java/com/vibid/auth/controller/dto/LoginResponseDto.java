package com.vibid.auth.controller.dto;

import com.vibid.auth.jwt.domain.AuthenticationResult;
import com.vibid.member.domain.Member;
import lombok.Getter;

@Getter
public class LoginResponseDto {
  private final String jwtToken;

  public LoginResponseDto(AuthenticationResult authenticationResult) {
    this.jwtToken = authenticationResult.getApiToken();
  }

  public LoginResponseDto(String token, Member member) {
    this.jwtToken = token;
  }
}
