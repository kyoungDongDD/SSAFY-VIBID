package com.vibid.bid.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TradeInfo {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "trade_info_id")
  private Long id;

  private String name;

  @Builder.Default
  private String address = null;
  @Builder.Default
  private String zipcode = null;

  @Builder.Default
  private String account = null;
  @Builder.Default
  private String bank = null;

  private String contact;

  @OneToOne(mappedBy = "tradeInfo")
  private Trade trade;

  @Enumerated(EnumType.STRING)
  private TradeType type;

  public static TradeInfo buyer(String name, String address, String zipcode, String contact) {
    return TradeInfo.builder()
      .name(name)
      .address(address)
      .zipcode(zipcode)
      .contact(contact)
      .type(TradeType.BUY)
      .build();
  }

  public static TradeInfo seller(String name, String account, String bank, String contact) {
    return TradeInfo.builder()
      .name(name)
      .account(account)
      .bank(bank)
      .contact(contact)
      .type(TradeType.SELL)
      .build();
  }

  public void setTrade(Trade trade) {
    this.trade = trade;
  }

}
