package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.user.application.service.dto.UpdateUserPasswordCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePasswordCommand(
    @NotNull Long userId, @NotBlank String oldPassword, @NotBlank String newPassword) {

  public UpdateUserPasswordCommand toUpdateUserPasswordCommand() {
    return new UpdateUserPasswordCommand(oldPassword(), newPassword());
  }
}
