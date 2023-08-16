package io.perfume.api.user.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class FailedToUpdateEmailException extends CustomHttpException {
    public FailedToUpdateEmailException(Long userId) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "failed to update email", "failed to update email. UserId: " + userId,
                LogLevel.WARN);
    }
}
