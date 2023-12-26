package io.perfume.api.file.application.port.in.dto;

import io.perfume.api.file.domain.File;

public record FileResult(Long id, String url) {

  public static FileResult DEFAULT_THUMBNAIL = new FileResult(0L, "");

  public static FileResult from(final File file) {
    return new FileResult(file.getId(), file.getUrl());
  }
}
