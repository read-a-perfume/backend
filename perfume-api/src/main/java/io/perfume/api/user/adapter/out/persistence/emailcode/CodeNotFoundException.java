package io.perfume.api.user.adapter.out.persistence.emailcode;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class CodeNotFoundException extends CustomHttpException {
  public CodeNotFoundException(String message) {
    super(HttpStatus.BAD_REQUEST, message, message, LogLevel.INFO);
  }
}
