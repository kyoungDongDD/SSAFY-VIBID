package com.vibid.auth.domain;

import com.vibid.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "AUTHORITY_ID")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @Enumerated(value = EnumType.STRING)
  private Role authority;

  protected Authority(Role authority) {
    this.authority = authority;
  }
  // 생성 메서드
  public static Authority from(Role role) {
    return new Authority(role);
  }

  // 연관관계 메서드
  public void setMember(Member member) {
    this.member = member;
    this.member.getAuthorities().add(this);
  }
}
