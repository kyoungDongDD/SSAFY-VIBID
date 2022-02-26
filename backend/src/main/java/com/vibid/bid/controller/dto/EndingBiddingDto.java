package com.vibid.bid.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EndingBiddingDto {
  private final boolean success;
  private final Long buyerId;
  private final Long sellerId;
  private final Long price;

  public static EndingBiddingDto pass(Long buyerId, Long sellerId, Long price) {
    return new EndingBiddingDto(true,buyerId,sellerId,price);
  }

  public static EndingBiddingDto fail() {
    return new EndingBiddingDto(false,null,null,null);
  }

}
