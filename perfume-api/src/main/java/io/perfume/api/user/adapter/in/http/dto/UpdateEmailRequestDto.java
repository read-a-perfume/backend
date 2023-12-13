package io.perfume.api.user.adapter.in.http.dto;

import io.perfume.api.user.application.port.in.dto.UpdateEmailCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UpdateEmailRequestDto(@NotNull Boolean verified, @NotNull @Email String email) {
  public UpdateEmailCommand toCommand(Long userId) {
    return new UpdateEmailCommand(userId, verified, email);
  }
}
