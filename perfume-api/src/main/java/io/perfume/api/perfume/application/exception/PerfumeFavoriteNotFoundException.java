package io.perfume.api.perfume.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class PerfumeFavoriteNotFoundException extends CustomHttpException {

  public PerfumeFavoriteNotFoundException(Long userId, Long perfumeId) {
    super(HttpStatus.NOT_FOUND,
        "cannot find user with id: " + userId + "and perfume with id: " + perfumeId,
        "cannot find user with id: " + userId + "and perfume with id: " + perfumeId,
        LogLevel.WARN);
  }
}
