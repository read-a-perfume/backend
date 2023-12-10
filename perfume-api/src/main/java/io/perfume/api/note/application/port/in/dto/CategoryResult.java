package io.perfume.api.note.application.port.in.dto;

import io.perfume.api.note.domain.Category;

public record CategoryResult(
    Long id, String name, String description, String tags, String thumbnail) {

  public static CategoryResult from(Category category) {
    return new CategoryResult(
        category.getId(), category.getName(), category.getDescription(), category.getTags(), "");
  }
}
