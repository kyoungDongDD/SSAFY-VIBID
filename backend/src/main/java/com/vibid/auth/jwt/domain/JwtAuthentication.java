package com.vibid.auth.jwt.domain;

import com.vibid.member.domain.Email;
import lombok.Getter;

@Getter
public class JwtAuthentication {

  private final Long id;
  private final Email email;
  private final String nickname;

  private JwtAuthentication(Long memberId, Email email, String nickname) {
    this.id = memberId;
    this.email = email;
    this.nickname = nickname;
  }

  public static JwtAuthentication of(final Long memberId, final Email email, final String nickname) {
    isNotNull(memberId);
    isNotNull(email);
    isNotNull(nickname);

    return new JwtAuthentication(memberId, email, nickname);
  }

  // Validation
  private static void isNotNull(Object obj) {
    if(obj == null)
      throw new IllegalArgumentException("parameter must be provided");
  }
}
