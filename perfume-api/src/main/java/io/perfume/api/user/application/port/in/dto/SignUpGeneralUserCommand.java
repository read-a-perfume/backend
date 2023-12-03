package io.perfume.api.user.application.port.in.dto;

public record SignUpGeneralUserCommand(
    String username,
    String password,
    String email,
    boolean marketingConsent,
    boolean promotionConsent) {}
