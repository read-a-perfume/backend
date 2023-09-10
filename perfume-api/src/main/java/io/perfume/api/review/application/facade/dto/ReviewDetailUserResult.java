package io.perfume.api.review.application.facade.dto;

import io.perfume.api.user.application.port.in.dto.UserResult;

public record ReviewDetailUserResult(
    Long id,
    String name
) {

  static public ReviewDetailUserResult from(UserResult user) {
    return new ReviewDetailUserResult(user.id(), user.name());
  }
}
