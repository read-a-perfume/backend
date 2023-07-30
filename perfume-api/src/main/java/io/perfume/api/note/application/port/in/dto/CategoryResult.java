package io.perfume.api.note.application.port.in.dto;

import io.perfume.api.note.domain.Category;

public record CategoryResult(Long id, String name, String thumbnail, String description) {

  public static CategoryResult from(Category note) {
    return new CategoryResult(note.getId(), note.getName(), "", note.getDescription());
  }
}
