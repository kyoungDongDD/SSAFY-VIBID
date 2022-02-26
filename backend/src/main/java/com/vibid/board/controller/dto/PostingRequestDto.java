package com.vibid.board.controller.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostingRequestDto {
  private String title;
  private String content;
  private String thumbnailImageUrl;
  private List<String> contentImageUrl;
  private List<String> tags;
  private Long startingPrice;
  private Long bidding;
  private LocalDateTime bidStartTime;
}
