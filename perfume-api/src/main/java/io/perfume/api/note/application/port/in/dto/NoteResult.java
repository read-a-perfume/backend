package io.perfume.api.note.application.port.in.dto;

import io.perfume.api.note.domain.Note;

public record NoteResult(Long id, String name, String thumbnail, String description) {

  public static NoteResult from(Note note) {
    return new NoteResult(note.getId(), note.getName(), "", note.getDescription());
  }
}
