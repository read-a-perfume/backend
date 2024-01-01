package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.note.application.port.in.dto.CategoryResult;

public record UserTypeResult(Long id, String name, String thumbnail, String description) {

  public static UserTypeResult from(CategoryResult categoryResult) {
    return new UserTypeResult(
        categoryResult.id(),
        categoryResult.name(),
        categoryResult.thumbnail(),
        categoryResult.description());
  }
}
