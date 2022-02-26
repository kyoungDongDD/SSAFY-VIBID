package com.vibid.bid.domain;

import com.vibid.board.domain.Board;
import com.vibid.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Trade {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "trade_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "trade_info_id")
  private TradeInfo tradeInfo;

  private Long price;

  @Enumerated(EnumType.STRING)
  private TradeType tradeType;

  @Builder.Default
  private boolean isConfirm = false;

  @Builder.Default
  private boolean isEnd = false;

  public static Trade from(Member member, Board board, Long price, TradeType tradeType) {
    return Trade.builder()
      .board(board)
      .member(member)
      .price(price)
      .tradeType(tradeType)
      .build();
  }

  public static Trade from(Member member, Board board) {
    return Trade.builder()
      .board(board)
      .member(member)
      .build();
  }

  public void setTradeInfo(TradeInfo tradeInfo) {
    this.tradeInfo = tradeInfo;
    this.tradeInfo.setTrade(this);
  }

  public void setConfirm() {
    this.isConfirm = true;
  }

  public void setBoard(Board board) {
    this.board = board;
    this.board.getTrades().add(this);
  }

  public void setEnd() {
    this.isEnd = true;
  }

  public void clearInfo() {
    // TODO CHECK
    this.tradeInfo = null;
  }
}
