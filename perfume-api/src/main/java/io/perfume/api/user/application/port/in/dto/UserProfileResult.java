package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.file.application.port.in.dto.FileResult;
import java.util.Optional;

public record UserProfileResult(Long userId, String username, String thumbnail) {

  private static final String DEFAULT_THUMBNAIL = "";

  public static UserProfileResult from(
      final UserResult user, final Optional<FileResult> thumbnail) {
    return new UserProfileResult(
        user.id(), user.username(), thumbnail.map(FileResult::url).orElse(DEFAULT_THUMBNAIL));
  }
}
