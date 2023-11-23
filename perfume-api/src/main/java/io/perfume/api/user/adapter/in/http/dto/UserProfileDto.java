package io.perfume.api.user.adapter.in.http.dto;

import io.perfume.api.user.application.port.in.dto.UserProfileResult;

public record UserProfileDto(Long userId, String username, String thumbnailUrl) {
  public static UserProfileDto of(UserProfileResult userProfileResult) {
    return new UserProfileDto(userProfileResult.userId(), userProfileResult.username(), userProfileResult.thumbnailUrl());
  }
}
