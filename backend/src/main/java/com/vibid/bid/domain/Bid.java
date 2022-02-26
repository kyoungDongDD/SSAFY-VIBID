package com.vibid.bid.domain;

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
public class Bid {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "BID_ID")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BOARD_ID")
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  private long price;

  @Builder.Default
  private LocalDateTime registTime = LocalDateTime.now();

  // 연관관계 메서드
  public void setMember(Member member) {
    this.member = member;
  }

  public void setBoard(Board board) {
    this.board = board;
  }
}
