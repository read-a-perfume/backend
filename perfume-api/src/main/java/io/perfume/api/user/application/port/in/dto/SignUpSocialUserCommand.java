package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.user.domain.SocialProvider;

public record SignUpSocialUserCommand(
    String identifier,
    String email,
    String username,
    String password,
    String name,
    SocialProvider socialProvider
) {
}
