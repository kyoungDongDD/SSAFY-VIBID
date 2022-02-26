package com.vibid.board.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

//세션에 필요한 정보 Dto
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardForSessionDto {

  private final Long id;
  private final Long authorId;
  private final String title;
  private final LocalDateTime bidStartTime;
  private final boolean isEnded;

}