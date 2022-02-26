package com.vibid.bid.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BuyerDto {
  private final Long boardId;
  private final String name;
  private final String address;
  private final String zipcode;
  private final String contact;
  private final Long price;
}
