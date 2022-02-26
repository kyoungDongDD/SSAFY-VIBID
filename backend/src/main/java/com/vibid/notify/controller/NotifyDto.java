package com.vibid.notify.controller;

import com.vibid.notify.domain.Notify;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotifyDto {

  private final Long notifyId;
  private final Long boardId;
  private final String message;
  private final LocalDateTime registDate;
  private final boolean isRead;

  private NotifyDto(Long notifyId, Long boardId, String message, LocalDateTime registDate, boolean isRead) {
    this.notifyId = notifyId;
    this.boardId = boardId;
    this.message = message;
    this.registDate = registDate;
    this.isRead = isRead;
  }

  public static NotifyDto of(Notify notify) {
    return new NotifyDto(notify.getId(),
      notify.getBoard().getId(),
      notify.getContent(),
      notify.getRegistDate(),
      notify.isRead());
  }
}
