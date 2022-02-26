package com.vibid.chat.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ResponseChatDto {
  private final String nickname;
  private final String content;
  private final LocalDateTime time;

  public static ResponseChatDto createResponse(String nickname, String content) {
    return ResponseChatDto.builder()
      .nickname(nickname)
      .content(content)
      .time(LocalDateTime.now())
      .build();
  }
}
