package io.perfume.api.note.adapter.in.http.dto;

import io.perfume.api.note.application.port.in.dto.CategoryResult;

public record NoteResponse(Long id, String name, String category) {

  public static NoteResponse from(CategoryResult categoryResult) {
    return new NoteResponse(categoryResult.id(), categoryResult.name(), categoryResult.category().toString());
  }
}
