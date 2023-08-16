package io.perfume.api.review.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Tag extends BaseTimeDomain {

  private final Long id;

  private final String name;

  public Tag(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt,
             LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
  }
}
