package io.perfume.api.user.application.port.in.dto;

public record UpdateEmailCommand(Long userId, Boolean verified, String email) {
}
