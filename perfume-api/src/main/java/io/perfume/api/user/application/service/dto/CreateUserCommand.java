package io.perfume.api.user.application.service.dto;

import java.time.LocalDateTime;

public record CreateUserCommand(
    String username,
    String email,
    String password,
    Boolean marketingConsent,
    Boolean promotionConsent,
    LocalDateTime registrationDateTime) {}
