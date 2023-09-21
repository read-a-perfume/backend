package io.perfume.api.perfume.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class PerfumeNotFoundException extends CustomHttpException {
    public PerfumeNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "cannot find perfume by id: "+id, "cannot find perfume by id: "+id, LogLevel.WARN);
    }
}
