package com.vibid.bid.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SellerDto {
  private final Long boardId;
  private final String name;
  private final String account;
  private final String bank;
  private final String contact;
  private final Long price;
}
