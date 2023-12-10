package io.perfume.api.user.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomHttpException {
  public UserNotFoundException(Long userId) {
    super(
        HttpStatus.NOT_FOUND,
        "cannot find user.",
        "cannot find user. userId : " + userId,
        LogLevel.WARN);
  }
}
