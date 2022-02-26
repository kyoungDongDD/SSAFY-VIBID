package com.vibid.member.controller.dto;

import com.vibid.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

@Getter
public class MemberDetailDto {
  private final String email;
  private final String nickname;
  private final String profileImageUrl;

  public MemberDetailDto(Member member) {
    this.email = member.getPrincipal().toString();
    this.nickname = member.getNickname();
    this.profileImageUrl = member.getProfileImageUrl();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
