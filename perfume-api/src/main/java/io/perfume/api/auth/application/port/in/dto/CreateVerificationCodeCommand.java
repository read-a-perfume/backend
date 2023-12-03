package io.perfume.api.auth.application.port.in.dto;

import java.time.LocalDateTime;

public record CreateVerificationCodeCommand(String metadata, LocalDateTime now) {}
