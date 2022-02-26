package com.vibid.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
  private String principal;

  private Email(String principal) {
    this.principal = principal;
  }

  public static Email from(final String address) {
    isNotEmpty(address);
    if(!checkAddress(address)) {
      throw new IllegalArgumentException("이메일 주소가 유효하지 않습니다.");
    }
    checkLength(address);
    Email email = new Email(address);

    email.checkName();
    email.checkDomain();

    return email;
  }

  public static Email deletedEmail(String name) {
    return new Email(name+"@deleted.user");
  }

  private static void isNotEmpty(final String address) {
    if ( StringUtils.isBlank(address))
      throw new IllegalArgumentException("해당 값은 비어있을 수 없습니다.");
  }

  private static void checkLength(final String address) {
    if(address.length() < 4 || address.length() > 320)
      throw new IllegalArgumentException("이메일 주소는 8~20자 조건을 만족해야합니다.");
  }

  private static boolean checkAddress(final String address) {
    return (Pattern.matches("^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+",address)
      && !Pattern.matches("^[A-Za-z0-9_\\.\\-]+@deleted.user", address));
  }

  private String splitNameByAddress() {
    String[] split = this.principal.split("@");
    return split[0];
  }

  private String splitDomainByAddress() {
    String[] split = this.principal.split("@");
    return split[1];
  }

  private void checkName() {
    final String name = splitNameByAddress();
    if(name.length() > 64)
      throw new IllegalArgumentException("이름이 너무 깁니다");
  }

  private void checkDomain() {
    final String domain = splitDomainByAddress();
    if(domain.length() > 255)
      throw new IllegalArgumentException("도메인이 너무 깁니다");
  }

  @Override
  public String toString() {
    return this.principal;
  }
}
