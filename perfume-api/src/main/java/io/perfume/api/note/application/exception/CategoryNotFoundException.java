package io.perfume.api.note.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends CustomHttpException {
  public CategoryNotFoundException(Long id) {
    super(
        HttpStatus.NOT_FOUND,
        "id (" + id + ")에 해당하는 카테고리를 찾을 수 없습니다.",
        "id (" + id + ")에 해당하는 카테고리를 찾을 수 없습니다.",
        LogLevel.INFO);
  }
}
