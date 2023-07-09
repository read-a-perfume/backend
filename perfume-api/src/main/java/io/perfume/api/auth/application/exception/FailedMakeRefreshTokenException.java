package io.perfume.api.auth.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class FailedMakeRefreshTokenException extends CustomHttpException {
    public FailedMakeRefreshTokenException(Exception e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "갱신 토큰을 생성할 수 없습니다.", e.getMessage(), LogLevel.WARN);
    }
}
