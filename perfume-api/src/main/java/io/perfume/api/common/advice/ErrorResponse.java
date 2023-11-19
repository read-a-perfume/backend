package io.perfume.api.common.advice;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
  private String status;
  private int statusCode;
  private String message;

  @Builder
  public ErrorResponse(String status, int statusCode, String message) {
    this.status = status;
    this.statusCode = statusCode;
    this.message = message;
  }
}
