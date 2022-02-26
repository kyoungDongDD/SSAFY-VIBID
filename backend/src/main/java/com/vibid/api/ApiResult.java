package com.vibid.api;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResult<T> {

  private final boolean isSuccess;

  private final T response;

  private final ApiError error;

  private ApiResult(boolean isSuccess, T response, ApiError error) {
    this.isSuccess = isSuccess;
    this.response = response;
    this.error = error;
  }

  public static<T> ApiResult<T> OK(T response) {
    return new ApiResult<>(true,response,null);
  }

  public static ApiResult<?> ERROR(Throwable throwable, HttpStatus status) {
    return new ApiResult<>(false,null,new ApiError(throwable,status));
  }

  public static ApiResult<?> ERROR(String message, HttpStatus status) {
    return new ApiResult<>(false,null,new ApiError(message,status));
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
