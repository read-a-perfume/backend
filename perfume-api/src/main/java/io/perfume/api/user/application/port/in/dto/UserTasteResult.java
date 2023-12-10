package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.note.application.port.in.dto.CategoryResult;

public record UserTasteResult(Long id, String name, String thumbnail, String description) {

  public static UserTasteResult from(CategoryResult categoryResult) {
    return new UserTasteResult(
        categoryResult.id(),
        categoryResult.name(),
        categoryResult.thumbnail(),
        categoryResult.description());
  }
}
