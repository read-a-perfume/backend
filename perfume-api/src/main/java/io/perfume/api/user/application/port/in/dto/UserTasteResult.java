package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.note.application.port.in.dto.NoteResult;

public record UserTasteResult(Long id, String name, String category) {

  static public UserTasteResult from(NoteResult noteResult) {
    return new UserTasteResult(noteResult.id(), noteResult.name(), noteResult.category().toString());
  }
}
