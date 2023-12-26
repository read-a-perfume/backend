package io.perfume.api.user.application.port.in.dto;

import java.time.LocalDateTime;

public record UserResult(
    long id, String username, Long thumbnailId, String email, LocalDateTime createdAt) {

  public static UserResult EMPTY = new UserResult(0L, "(알 수 없음)", null, "", LocalDateTime.now());
}
