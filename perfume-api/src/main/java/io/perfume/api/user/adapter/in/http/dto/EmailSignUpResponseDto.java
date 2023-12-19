package io.perfume.api.user.adapter.in.http.dto;

import io.perfume.api.user.application.port.in.dto.UserResult;

public record EmailSignUpResponseDto(long id, String username, String email) {

  public static EmailSignUpResponseDto from(final UserResult user) {
    return new EmailSignUpResponseDto(user.id(), user.username(), user.email());
  }
}
