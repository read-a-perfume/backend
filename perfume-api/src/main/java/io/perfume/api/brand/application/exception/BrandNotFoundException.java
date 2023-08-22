package io.perfume.api.brand.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class BrandNotFoundException extends CustomHttpException {
    public BrandNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "cannot find brand.", "cannot find brand. id: "+id, LogLevel.WARN);
    }
}
