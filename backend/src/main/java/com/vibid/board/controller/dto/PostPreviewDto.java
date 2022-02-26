package com.vibid.board.controller.dto;

import com.vibid.board.domain.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostPreviewDto {
  private final Long id;

  private final Long memberId;

  private final String authorNickname;

  private final String thumbnailImageUrl;

  private final String title;

  private final int wishCount;

  private final int viewCount;

  private final Long startingPrice;

  private final LocalDateTime registTime;

  private final boolean isWish;

  public static PostPreviewDto from(Board board) {
    return PostPreviewDto.builder()
      .id(board.getId())
      .memberId(board.getMember().getId())
      .authorNickname(board.getMember().getNickname())
      .thumbnailImageUrl(board.getThumbnailImageUrl())
      .title(board.getTitle())
      .wishCount(board.getWishCount())
      .viewCount(board.getViewCount())
      .startingPrice(board.getBidInfo().getStartingPrice())
      .registTime(board.getRegistDate())
      .isWish(board.isWish())
      .build();
  }
}
