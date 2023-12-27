package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.user.adapter.out.persistence.user.Sex;
import io.perfume.api.user.domain.User;

public record UserProfileResult(Long id, String username, String bio, Sex sex, String thumbnail) {
  public static UserProfileResult from(User user, String thumbnail) {
    return new UserProfileResult(
        user.getId(),
        user.getUsername(),
        user.getBio(),
        user.getSex(),
        thumbnail
    );
  }
}
