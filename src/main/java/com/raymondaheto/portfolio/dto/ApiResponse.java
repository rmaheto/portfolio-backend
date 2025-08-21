package com.raymondaheto.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

  private boolean success;
  private String code;
  private String message;
  private T data;
  private Instant timestamp;

  public static <T> ApiResponse<T> ok(final T data) {
    return ApiResponse.<T>builder().success(true).data(data).timestamp(Instant.now()).build();
  }

  public static <T> ApiResponse<T> created(final T data) {
    return ApiResponse.<T>builder().success(true).data(data).timestamp(Instant.now()).build();
  }

  public static <T> ApiResponse<T> error(final String message) {
    return ApiResponse.<T>builder()
        .success(false)
        .message(message)
        .timestamp(Instant.now())
        .build();
  }
}
