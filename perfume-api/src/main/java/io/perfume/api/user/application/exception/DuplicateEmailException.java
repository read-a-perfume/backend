package io.perfume.api.user.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends CustomHttpException {

  public DuplicateEmailException() {
    super(HttpStatus.BAD_REQUEST, "중복된 이메일입니다.", "중복된 이메일입니다.", LogLevel.INFO);
  }
}
