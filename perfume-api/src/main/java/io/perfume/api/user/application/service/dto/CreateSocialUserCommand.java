package io.perfume.api.user.application.service.dto;

import java.time.LocalDateTime;

public record CreateSocialUserCommand(
    String username,
    String email,
    String password,
    boolean marketingConsent,
    boolean promotionConsent,
    String identifier,
    LocalDateTime registrationDateTime) {}
