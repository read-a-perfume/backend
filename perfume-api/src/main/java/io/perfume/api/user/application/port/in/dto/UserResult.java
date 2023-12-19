package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;

public record UserResult(
    Long id, String username, String email, Long thumbnailId, LocalDateTime createdAt) {

  public static UserResult EMPTY = new UserResult(0L, "(알 수 없음)", "", 1L, LocalDateTime.now());

  public static UserResult of(User user) {
    return new UserResult(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getThumbnailId(),
        user.getCreatedAt());
  }
}
