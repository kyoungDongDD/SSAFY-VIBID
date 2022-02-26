package com.vibid.utils;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtils {
  private static MessageSourceAccessor messageSourceAccessor;

  public static String getMessage(String key) {
    isNotNull();
    return messageSourceAccessor.getMessage(key);
  }

  public static String getMessage(String key, Object... params) {
    isNotNull();
    return messageSourceAccessor.getMessage(key, params);
  }

  public static void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
    MessageUtils.messageSourceAccessor = messageSourceAccessor;
  }

  public static void isNotNull() {
    if(messageSourceAccessor == null)
      throw new IllegalStateException("MessageSourceAccessor is not initialized.");
  }
}
