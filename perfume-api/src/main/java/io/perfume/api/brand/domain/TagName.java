package io.perfume.api.brand.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TagName extends BaseTimeDomain {

  private final Long id;

  private final String name;

  public TagName(
      Long id,
      String name,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
  }

  public static TagName create(String name, LocalDateTime now) {
    return new TagName(null, name, now, now, null);
  }
}
