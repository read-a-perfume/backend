package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.file.application.port.in.dto.FileResult;

public record UpdateProfilePicResult(Long userId, Long fileId, String url) {

  public static UpdateProfilePicResult from(Long userId, FileResult fileResult) {
    return new UpdateProfilePicResult(userId, fileResult.id(), fileResult.url());
  }
}
