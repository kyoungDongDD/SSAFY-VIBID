package com.vibid.board.controller.dto;

import com.vibid.board.domain.BidInfo;
import com.vibid.board.domain.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDto {
  // board 테이블
  private final Long id;

  private final Long memberId;

  private final String nickname;

  private final String imagesLocation;

  private final String title;

  private final String content;

  private final int viewCount;

  private final int wishCount;

  private final LocalDateTime registDate;

  private final Boolean isLiftUp;

  private final Boolean isLive;

  private final boolean isWish;

  //해시태그 리스트
  private final List<String> tagName;

  //bid_info 테이블
  private final Long startingPrice;      // 입찰 시작가

  private final Long endingPrice;        // 낙찰금액

  private final Boolean isEnded;            // 낙찰 유무

  private final Long bidding;            // 최저 호가

  private final LocalDateTime bidStartTime; // 경매 시작시간

  public static PostDto from(Board board) {
    BidInfo bidInfo = board.getBidInfo();
    return PostDto.builder()
      .id(board.getId())
      .memberId(board.getMember().getId())
      .nickname(board.getMember().getNickname())
      .imagesLocation(board.getThumbnailImageUrl())
      .title(board.getTitle())
      .content(board.getContent())
      .viewCount(board.getViewCount())
      .wishCount(board.getWishCount())
      .registDate(board.getRegistDate())
      .isLiftUp(board.getIsLiftUp())
      .isLive(board.getIsLive())
      .isWish(board.isWish())
      .tagName(board.getHashtags())
      .startingPrice(bidInfo.getStartingPrice())
      .endingPrice(bidInfo.getEndingPrice())
      .isEnded(bidInfo.getEndingPrice() == null ? false : true)
      .bidding(bidInfo.getBidding())
      .bidStartTime(bidInfo.getBidStartTime())
      .build();
  }
}
