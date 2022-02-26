package com.vibid.member.controller.dto;

import com.vibid.member.domain.Email;
import com.vibid.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@RequiredArgsConstructor
public class MemberDto {
  private final Long id;
  private final String email;
  private final String nickname;

  public MemberDto(Member member) {
    this.id = member.getId();
    this.email = member.getPrincipal().toString();
    this.nickname = member.getNickname();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
