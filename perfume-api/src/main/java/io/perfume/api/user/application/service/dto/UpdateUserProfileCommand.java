package io.perfume.api.user.application.service.dto;

import io.perfume.api.user.adapter.out.persistence.user.Sex;
import java.time.LocalDate;

public record UpdateUserProfileCommand(
    String bio, LocalDate birthday, Sex sex, Long thumbnailId, String email) {

  public static UpdateUserProfileCommand createUpdateThumbnail(final long thumbnailId) {
    return new UpdateUserProfileCommand(null, null, null, thumbnailId, null);
  }

  public static UpdateUserProfileCommand createUpdateEmail(final String email) {
    return new UpdateUserProfileCommand(null, null, null, null, email);
  }
}
