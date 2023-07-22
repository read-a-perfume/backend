package io.perfume.api.note.application.port.in.dto;

import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.NoteCategory;

public record NoteResult(Long id, String name, NoteCategory category) {

  public static NoteResult from(Note note) {
    return new NoteResult(note.getId(), note.getName(), note.getCategory());
  }
}
