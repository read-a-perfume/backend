package io.perfume.api.file.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class File extends BaseTimeDomain {

  private final Long id;

  private final String url;

  private final Long userId;

  @Builder
  private File(Long id, String url, Long userId,
               LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);

    this.id = id;
    this.url = url;
    this.userId = userId;
  }

  public static File createFile(String url, Long userId, LocalDateTime now) {
    return File.builder()
        .url(url)
        .userId(userId)
        .createdAt(now)
        .updatedAt(now)
        .deletedAt(null)
        .build();
  }


}
