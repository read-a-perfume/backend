package io.perfume.api.auth.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class NotFoundRefreshTokenException extends CustomHttpException {
    public NotFoundRefreshTokenException() {
        super(HttpStatus.NOT_FOUND, "로그인 상태가 아닙니다.", "어떤 것을 넣나요?^^;", LogLevel.ERROR);
    }
}
