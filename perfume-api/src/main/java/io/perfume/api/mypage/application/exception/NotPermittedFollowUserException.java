package io.perfume.api.mypage.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class NotPermittedFollowUserException extends CustomHttpException {

  public NotPermittedFollowUserException() {
    super(HttpStatus.NOT_FOUND, "허용되지 않는 작업입니다.", "", LogLevel.ERROR);
  }
}
