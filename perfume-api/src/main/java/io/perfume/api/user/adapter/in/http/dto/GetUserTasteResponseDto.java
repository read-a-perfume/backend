package io.perfume.api.user.adapter.in.http.dto;

import io.perfume.api.user.application.port.in.dto.UserTasteResult;

public record GetUserTasteResponseDto(Long id, String name, String description, String thumbnail) {

  public static GetUserTasteResponseDto from(UserTasteResult userTasteResult) {
    return new GetUserTasteResponseDto(
        userTasteResult.id(),
        userTasteResult.name(),
        userTasteResult.description(),
        userTasteResult.thumbnail());
  }
}
