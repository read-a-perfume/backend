package io.perfume.api.review.application.facade.dto;

import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.user.application.port.in.dto.UserResult;
import java.util.List;

public record ReviewCommentUserResult(
    Long id,
    String name
) {

  public static ReviewCommentUserResult from(UserResult user) {
    return new ReviewCommentUserResult(user.id(), user.username());
  }
}
