package com.vibid.notify.domain;

import com.vibid.board.domain.Board;
import com.vibid.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Notify {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notify_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  private String content;

  @Builder.Default
  private boolean isRead = false;

  @Builder.Default
  private LocalDateTime registDate = LocalDateTime.now();

  public static Notify from(String content) {
    return Notify.builder()
      .content(content)
      .build();
  }

  public Notify read() {
    this.isRead = true;
    return this;
  }

}
