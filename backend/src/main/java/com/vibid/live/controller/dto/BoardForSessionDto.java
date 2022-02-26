package com.vibid.live.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
public class BoardForSessionDto {
  private final Long id;
  private final Long authorId;
  private final String title;
  private final LocalDateTime bidStartTime;
  private final boolean isEnded;
}
