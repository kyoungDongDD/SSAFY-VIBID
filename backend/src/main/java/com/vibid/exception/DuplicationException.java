package com.vibid.exception;

import com.vibid.utils.MessageUtils;
import org.apache.commons.lang3.StringUtils;

public class DuplicationException extends ServiceRuntimeException{

  static final String MESSAGE_KEY = "error.duplication";

  static final String MESSAGE_DETAILS = "error.duplication.details";

  public DuplicationException(Class<?> cls, Object... values) {
    this(cls.getSimpleName(), values);
  }

  public DuplicationException(String targetName, Object... values) {
    super(MESSAGE_KEY, MESSAGE_DETAILS, new String[]{targetName, (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
  }

  @Override
  public String getMessage() {
    return MessageUtils.getMessage(getDetailKey(), getParams());
  }

  @Override
  public String toString() {
    return MessageUtils.getMessage(getMessageKey());
  }
}
