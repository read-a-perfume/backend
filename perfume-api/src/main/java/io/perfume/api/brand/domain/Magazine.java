package io.perfume.api.brand.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Magazine extends BaseTimeDomain {

  private final Long id;

  private final String title;

  private final String subTitle;

  private final String content;

  private final Long coverThumbnailId;

  private final Long thumbnailId;

  private final Long userId;

  private final Long brandId;

  public Magazine(
      Long id,
      String title,
      String subTitle,
      String content,
      Long coverThumbnailId,
      Long thumbnailId,
      Long userId,
      Long brandId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.content = content;
    this.coverThumbnailId = coverThumbnailId;
    this.thumbnailId = thumbnailId;
    this.userId = userId;
    this.brandId = brandId;
  }

  public static Magazine create(
      String title,
      String subTitle,
      String content,
      Long coverThumbnailId,
      Long thumbnailId,
      Long userId,
      Long brandId,
      LocalDateTime now) {
    return new Magazine(
        null,
        title,
        subTitle,
        content,
        coverThumbnailId,
        thumbnailId,
        userId,
        brandId,
        now,
        now,
        null);
  }
}
