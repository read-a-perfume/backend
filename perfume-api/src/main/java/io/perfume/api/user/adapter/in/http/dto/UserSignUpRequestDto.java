package io.perfume.api.user.adapter.in.http.dto;

import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record UserSignUpRequestDto(
    @NotBlank String username,
    @NotBlank String password,
    @Email String email,
    boolean marketingConsent,
    boolean promotionConsent) {

  public SignUpGeneralUserCommand toCommand(final LocalDateTime now) {
    return new SignUpGeneralUserCommand(
        username, password, email, marketingConsent, promotionConsent, now);
  }
}
