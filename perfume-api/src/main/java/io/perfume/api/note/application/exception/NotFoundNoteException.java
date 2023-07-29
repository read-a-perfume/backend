package io.perfume.api.note.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class NotFoundNoteException extends CustomHttpException {

  public NotFoundNoteException(Long noteId) {
    super(HttpStatus.NOT_FOUND, "존재하지 않는 노트입니다.", "NotFoundNoteException noteId=" + noteId, LogLevel.INFO);
  }
}
