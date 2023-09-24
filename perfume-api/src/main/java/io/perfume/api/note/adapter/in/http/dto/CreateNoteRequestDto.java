package io.perfume.api.note.adapter.in.http.dto;

import io.perfume.api.note.application.port.in.dto.CreateNoteCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNoteRequestDto(@NotBlank String name, @NotBlank String description, @NotNull Long thumbnailId) {
  public CreateNoteCommand toCommand() {
    return new CreateNoteCommand(name, description, thumbnailId);
  }
}
