package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.user.application.service.dto.UpdateUserProfileCommand;

public record UpdateEmailCommand(Long userId, Boolean verified, String email) {

  public UpdateUserProfileCommand toUpdateUserEmailCommand() {
    return UpdateUserProfileCommand.createUpdateEmail(email);
  }
}
