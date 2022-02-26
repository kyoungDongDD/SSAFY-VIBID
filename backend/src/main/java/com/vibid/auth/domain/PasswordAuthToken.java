package com.vibid.auth.domain;

import com.vibid.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordAuthToken {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @Column(name = "password_auth_token", length = 32)
  private String token;

  private LocalDateTime expiration;

  @OneToOne
  @JoinColumn(name = "member_id")
  private Member member;

  private LocalDateTime createDate;


  protected PasswordAuthToken(LocalDateTime expiration, LocalDateTime createDate) {
    this.expiration = expiration;
    this.createDate = createDate;
  }

  public static PasswordAuthToken of(Long expiryDate) {
    LocalDateTime createDate = LocalDateTime.now();
    return new PasswordAuthToken(createDate.plusDays(expiryDate), createDate);
  }

  // 연관 관계 메서드
  public void setMember(Member member) {
    this.member = member;
  }

}
