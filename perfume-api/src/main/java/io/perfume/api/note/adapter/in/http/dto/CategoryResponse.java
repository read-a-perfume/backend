package io.perfume.api.note.adapter.in.http.dto;

import io.perfume.api.note.application.port.in.dto.CategoryResult;

public record CategoryResponse(Long id, String name, String description, String tags, String thumbnail) {
  public static CategoryResponse from(CategoryResult categoryResult) {
    return new CategoryResponse(categoryResult.id(), categoryResult.name(), categoryResult.description(), categoryResult.tags(),
        categoryResult.thumbnail());
  }
}
