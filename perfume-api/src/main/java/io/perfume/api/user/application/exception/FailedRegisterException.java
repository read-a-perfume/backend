package io.perfume.api.user.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class FailedRegisterException extends CustomHttpException {

  public FailedRegisterException() {
    super(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "회원 데이터 저장 실패로 인해 회원가입이 불가능합니다.",
        "failed to signup because of failure of saving user entity",
        LogLevel.WARN);
  }
}
