package com.vibid.auth.domain;

import com.vibid.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthToken {

  @Id @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "email_auth_token", length = 36)
  private String token;

  private LocalDateTime expiration;

  @OneToOne
  @JoinColumn(name = "member_id")
  private Member member;

  private LocalDateTime createDate;


  protected EmailAuthToken(LocalDateTime expiration, LocalDateTime createDate) {
    this.expiration = expiration;
    this.createDate = createDate;
  }

  public static EmailAuthToken of(Long expiryDate) {
    LocalDateTime createDate = LocalDateTime.now();
    return new EmailAuthToken(createDate.plusDays(expiryDate), createDate);
  }

  // 연관 관계 메서드
  public void setMember(Member member) {
    this.member = member;
  }


}

