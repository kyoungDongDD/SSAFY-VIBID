package com.vibid.member.domain;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

@Getter
public class Password {
  private final String credential;

  private Password(final String credential) {
    this.credential = credential;
  }

  public static Password from(final String password) {
    isNotEmpty(password);
    checkLength(password);
    if(!checkPassword(password))
      throw new IllegalArgumentException("비밀 번호 규칙 위반입니다.");
    return new Password(password);
  }

  public static Password of(final String password, final String confirm) {
    isNotEmpty(password);
    isNotEmpty(confirm);
    isSame(password, confirm);
    checkLength(password);
    if(!checkPassword(password))
      throw new IllegalArgumentException("비밀 번호 규칙 위반입니다.");
    return new Password(password);
  }

  private static void isNotEmpty(final String str) {
    if(StringUtils.isBlank(str))
      throw new IllegalArgumentException(str+"은(는) 비어있을 수 없습니다");
  }

  private static void checkLength(final String password) {
    if(password.length() < 8 || password.length() > 20)
      throw new IllegalArgumentException("비밀번호는 8~20자 조건을 만족해야합니다.");
  }

  private static void isSame(final String password, final String confirm) {
    if(!password.equals(confirm))
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
  }
  private static boolean checkPassword(final String password) {
    return Pattern.matches("^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=_-]).*$",password);
  }
}
