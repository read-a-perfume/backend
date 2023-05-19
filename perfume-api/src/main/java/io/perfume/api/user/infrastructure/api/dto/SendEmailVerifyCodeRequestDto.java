package io.perfume.api.user.infrastructure.api.dto;

import jakarta.validation.constraints.Email;

public record SendEmailVerifyCodeRequestDto (@Email String email) {
}
