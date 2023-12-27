package io.perfume.api.user.adapter.in.http.dto;

import io.perfume.api.user.adapter.out.persistence.user.Sex;
import io.perfume.api.user.application.port.in.dto.UserProfileResult;

public record UserProfileDto(Long id, String username, String bio, Sex sex, String thumbnail) {
  public static UserProfileDto of(UserProfileResult userProfileResult) {
    return new UserProfileDto(userProfileResult.id(), userProfileResult.username(), userProfileResult.bio(), userProfileResult.sex(),
        userProfileResult.thumbnail());
  }
}
