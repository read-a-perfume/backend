package io.perfume.api.file.adapter.in.http.dto;

import io.perfume.api.file.application.port.in.dto.FileResult;

public record UpdateFileResponseDto(long id, String url) {
  public static UpdateFileResponseDto from(FileResult result) {
    return new UpdateFileResponseDto(result.id(), result.url());
  }
}
