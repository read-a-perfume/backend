package io.perfume.api.mypage.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class AlreadyFollowException extends CustomHttpException {

  public AlreadyFollowException() {
    super(HttpStatus.CONFLICT, null, null, LogLevel.WARN);
  }
}
