package io.perfume.api.note.adapter.in.http.dto;

import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.note.application.port.in.dto.NoteResult;

public record NoteResponse(Long id, String name, String description, String thumbnail) {

  public static NoteResponse from(CategoryResult categoryResult) {
    return new NoteResponse(categoryResult.id(), categoryResult.name(),
        categoryResult.description(), categoryResult.thumbnail());
  }

  public static NoteResponse from(NoteResult noteResult) {
    return new NoteResponse(noteResult.id(), noteResult.name(),
        noteResult.description(), noteResult.thumbnail());
  }
}
