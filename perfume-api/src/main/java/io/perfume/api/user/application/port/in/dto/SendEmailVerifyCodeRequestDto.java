package io.perfume.api.user.application.port.in.dto;

import jakarta.validation.constraints.Email;

public record SendEmailVerifyCodeRequestDto (@Email String email) {
}
