package com.vibid.bid.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseBiddingDto {
  private final boolean success;
  private final String nickname;
  private final Long price;

  public static ResponseBiddingDto pass(String nickname, Long price) {
    return new ResponseBiddingDto(true,nickname,price);
  }

  public static ResponseBiddingDto fail() {
    return new ResponseBiddingDto(false,null,null);
  }
}
