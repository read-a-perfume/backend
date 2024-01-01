package io.perfume.api.user.adapter.in.http.dto;

import io.perfume.api.user.application.port.in.dto.UserTypeResult;

public record UserTypeResponseDto(Long id, String name, String description, String thumbnail) {

  public static UserTypeResponseDto from(UserTypeResult userTypeResult) {
    return new UserTypeResponseDto(
        userTypeResult.id(),
        userTypeResult.name(),
        userTypeResult.description(),
        userTypeResult.thumbnail());
  }
}
