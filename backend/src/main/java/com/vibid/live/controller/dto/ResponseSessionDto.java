package com.vibid.live.controller.dto;

import io.openvidu.java.client.Connection;
import lombok.Getter;

@Getter
public class ResponseSessionDto {
  private final String token;

  public ResponseSessionDto(String token) {
    this.token = token;
  }
}
