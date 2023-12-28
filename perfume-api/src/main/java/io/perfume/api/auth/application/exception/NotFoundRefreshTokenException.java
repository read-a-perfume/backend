package io.perfume.api.auth.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class NotFoundRefreshTokenException extends CustomHttpException {
  public NotFoundRefreshTokenException() {
    super(HttpStatus.NOT_FOUND, "Refresh Token을 찾을 수 없습니다.", "", LogLevel.ERROR);
  }
}
