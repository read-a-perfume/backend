package io.perfume.api.file.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;

import io.perfume.api.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

@Getter
public class File extends BaseTimeDomain {

  private final Long id;

  private final String url;

  @Builder(access = AccessLevel.PACKAGE)
  private File(Long id, String url, LocalDateTime createdAt, LocalDateTime updatedAt,
               LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);

    this.id = id;
    this.url = url;
  }

  // Only Adapter
  public static File withId(
          Long id, String url, LocalDateTime createdAt,
          LocalDateTime deletedAt, LocalDateTime updatedAt) {
      return File.builder()
              .id(id)
              .url(url)
              .createdAt(createdAt)
              .updatedAt(updatedAt)
              .deletedAt(deletedAt)
              .build();
  }

}
