package com.vibid.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BidInfo {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bid_info_id")
  private Long id;             //비드 인포 아이디

  @OneToOne(mappedBy = "bidInfo")
  private Board board;

  private Long startingPrice;      // 입찰 시작가

  private Long endingPrice;        // 낙찰금액

  private Long bidding;            // 최저 호가

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime bidStartTime; //경매 시작 시간


  public void update(Long startingPrice,Long bidding, LocalDateTime bidStartTime){
    this.startingPrice = startingPrice;
    this.bidding = bidding;
    this.bidStartTime = bidStartTime;
  }

  // 연관관계 메서드
  public void setBoard(Board board) {
    this.board = board;
  }

  public void setEndingPrice(long price) {
    this.endingPrice = price;
  }
}
