package io.perfume.api.file.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CustomHttpException {
    public FileNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "cannot find file.", "cannot find file. id: "+id, LogLevel.WARN);
    }
}
