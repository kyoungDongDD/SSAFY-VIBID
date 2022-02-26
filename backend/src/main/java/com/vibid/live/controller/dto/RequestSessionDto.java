package com.vibid.live.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestSessionDto {
  private Long boardId;
  private String token;     // default null
}
