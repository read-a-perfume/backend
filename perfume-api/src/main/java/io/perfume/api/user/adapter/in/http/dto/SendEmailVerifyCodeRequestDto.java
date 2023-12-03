package io.perfume.api.user.adapter.in.http.dto;

import jakarta.validation.constraints.Email;

public record SendEmailVerifyCodeRequestDto(@Email String email) {}
