package io.perfume.api.brand.adapter.in.http.dto;

import io.perfume.api.brand.application.port.in.dto.CreateBrandCommand;
import jakarta.validation.constraints.NotEmpty;

public record CreateBrandRequestDto(
    @NotEmpty String name, @NotEmpty String story, Long thumbnailId) {

  public CreateBrandCommand toCommand() {
    return new CreateBrandCommand(name, story, thumbnailId);
  }
}
