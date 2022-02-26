package com.vibid.api;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {
  private final int statusCode;
  private final String message;

  public ApiError(Throwable throwable, HttpStatus status) {
    this(throwable.getMessage(), status);
  }

  public ApiError(String message, HttpStatus status) {
    this.message = message;
    this.statusCode = status.value();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
