package io.perfume.api.brand.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MagazineTag extends BaseTimeDomain {

  private final Long magazineId;

  private final Long tagId;

  public MagazineTag(
      Long magazineId,
      Long tagId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.magazineId = magazineId;
    this.tagId = tagId;
  }

  public static MagazineTag create(Long magazineId, Long tagId, LocalDateTime now) {
    return new MagazineTag(magazineId, tagId, now, now, null);
  }
}
