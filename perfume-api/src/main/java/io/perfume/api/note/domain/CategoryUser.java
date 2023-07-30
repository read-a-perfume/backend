package io.perfume.api.note.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CategoryUser extends BaseTimeDomain {

  private final Long id;

  private final Long userId;

  private final Category category;

  public CategoryUser(Long id, Long userId, Category category, LocalDateTime createdAt,
                      LocalDateTime updatedAt,
                      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.userId = userId;
    this.category = category;
  }

  public static CategoryUser create(Long userId, Category category, LocalDateTime now) {
    return new CategoryUser(null, userId, category, now, now, null);
  }
}
