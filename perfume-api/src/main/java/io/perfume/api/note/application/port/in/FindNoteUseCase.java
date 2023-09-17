package io.perfume.api.note.application.port.in;

import io.perfume.api.note.application.port.in.dto.NoteResult;

public interface FindNoteUseCase {
  NoteResult findNoteById(Long noteId);
}
