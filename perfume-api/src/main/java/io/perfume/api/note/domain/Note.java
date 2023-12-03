package io.perfume.api.note.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Note extends BaseTimeDomain {

  private final Long id;

  private final String name;

  private final String description;

  private final Long thumbnailId;

  public Note(
      Long id,
      String name,
      String description,
      Long thumbnailId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
    this.description = description;
    this.thumbnailId = thumbnailId;
  }

  public static Note create(String name, String description, Long thumbnailId) {
    return new Note(null, name, description, thumbnailId, null, null, null);
  }
}
