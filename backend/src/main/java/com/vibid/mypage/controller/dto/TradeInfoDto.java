package com.vibid.mypage.controller.dto;

import com.vibid.bid.domain.Trade;
import com.vibid.bid.domain.TradeInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TradeInfoDto {
  private Long boardId;
  private String title;
  private Long price;

  // Seller
  private Long sellerId;
  private String sellerAccount;
  private String sellerBank;
  private String sellerName;
  private String sellerContact;

  // Buyer
  private Long buyerId;
  private String buyerAddress;
  private String buyerZipcode;
  private String buyerName;
  private String buyerContact;

  public static TradeInfoDto of(Long boardId, String title, Long price) {
    return TradeInfoDto.builder()
      .boardId(boardId)
      .title(title)
      .price(price)
      .build();
  }

  public void setSeller(Long memberId, TradeInfo tradeInfo) {
    this.sellerId = memberId;
    this.sellerAccount = tradeInfo.getAccount();
    this.sellerBank = tradeInfo.getBank();
    this.sellerName = tradeInfo.getName();
    this.sellerContact = tradeInfo.getContact();
  }

  public void setBuyer(Long memberId, TradeInfo tradeInfo) {
    this.buyerId = memberId;
    this.buyerAddress = tradeInfo.getAddress();
    this.buyerZipcode = tradeInfo.getZipcode();
    this.buyerName = tradeInfo.getName();
    this.buyerContact = tradeInfo.getContact();
  }
}
