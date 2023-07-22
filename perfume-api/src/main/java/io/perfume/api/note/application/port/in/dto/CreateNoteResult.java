package io.perfume.api.note.application.port.in.dto;

import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.NoteCategory;

public record CreateNoteResult(Long id, String name, NoteCategory category) {

  public static CreateNoteResult from(Note note) {
    return new CreateNoteResult(note.getId(), note.getName(), note.getCategory());
  }
}
