package io.perfume.api.auth.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class FailedMakeNewAccessTokenException extends CustomHttpException {
  public FailedMakeNewAccessTokenException(Exception e) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, "새로운 액세스 토큰 발급이 불가합니다.", e.getMessage(), LogLevel.WARN);
  }

  public FailedMakeNewAccessTokenException() {
    super(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "새로운 액세스 토큰 발급이 불가합니다.",
        "refresh token mismatch",
        LogLevel.WARN);
  }
}
