package io.perfume.api.auth.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class FailedMakeNewAccessTokenException extends CustomHttpException {
  public FailedMakeNewAccessTokenException() {
    super(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Refresh Token이 서버에 존재하지 않아 토큰 재발급이 불가능합니다.",
        "refresh token mismatch from server.",
        LogLevel.WARN);
  }
}
