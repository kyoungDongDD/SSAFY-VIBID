package com.vibid.auth.jwt.domain;

import com.vibid.member.domain.Member;
import lombok.Getter;

@Getter
public class AuthenticationResult {

  private final String apiToken;

  private final Member member;

  private AuthenticationResult(String apiToken, Member member) {
    this.apiToken = apiToken;
    this.member = member;
  }

  public static AuthenticationResult from(String apiToken, Member member) {
    isNotNull(apiToken);
    isNotNull(member);
    return new AuthenticationResult(apiToken, member);
  }

  private static void isNotNull(Object obj) {
    if(obj == null)
      throw new IllegalArgumentException("AuthenticationResult Error");
  }
}
