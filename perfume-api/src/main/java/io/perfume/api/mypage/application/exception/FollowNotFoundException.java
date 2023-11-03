package io.perfume.api.mypage.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class FollowNotFoundException extends CustomHttpException {

  public FollowNotFoundException() {
    super(HttpStatus.NOT_FOUND, null, null, LogLevel.WARN);
  }
}
