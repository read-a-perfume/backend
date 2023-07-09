package io.perfume.api.file.domain;

import io.perfume.api.base.BaseTimeDomain;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class File extends BaseTimeDomain {

  private final Long id;

  private final String url;

  @Builder
  private File(Long id, String url, LocalDateTime createdAt, LocalDateTime updatedAt,
               LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);

    this.id = id;
    this.url = url;
  }
}
