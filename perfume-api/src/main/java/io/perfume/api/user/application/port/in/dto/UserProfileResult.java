package io.perfume.api.user.application.port.in.dto;

public record UserProfileResult(Long userId, String username, String thumbnail) {
}
