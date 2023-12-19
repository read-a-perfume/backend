package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.user.application.service.dto.CreateUserCommand;
import java.time.LocalDateTime;

public record SignUpGeneralUserCommand(
    String username,
    String password,
    String email,
    boolean marketingConsent,
    boolean promotionConsent,
    LocalDateTime registrationDateTime) {

  public CreateUserCommand toCreateUserCommand() {
    return new CreateUserCommand(
        username, password, email, marketingConsent, promotionConsent, registrationDateTime);
  }
}
