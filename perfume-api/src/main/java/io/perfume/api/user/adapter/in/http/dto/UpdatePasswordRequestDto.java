package io.perfume.api.user.adapter.in.http.dto;

import io.perfume.api.user.application.port.in.dto.UpdatePasswordCommand;
import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequestDto(@NotBlank String oldPassword, @NotBlank String newPassword) {
  public UpdatePasswordCommand toCommand(Long userId) {
    return new UpdatePasswordCommand(userId, oldPassword, newPassword);
  }
}
