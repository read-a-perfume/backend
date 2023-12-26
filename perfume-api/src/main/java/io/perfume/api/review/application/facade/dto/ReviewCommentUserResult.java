package io.perfume.api.review.application.facade.dto;

import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.user.application.port.in.dto.UserResult;

public record ReviewCommentUserResult(long id, String thumbnail, String name) {

  public static ReviewCommentUserResult from(UserResult user, FileResult thumbnail) {
    return new ReviewCommentUserResult(user.id(), thumbnail.url(), user.username());
  }
}
