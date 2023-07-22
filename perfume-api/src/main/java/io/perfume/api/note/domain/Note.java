package io.perfume.api.note.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Note extends BaseTimeDomain {

  private final Long id;

  private String name;

  private Long thumbnailId;

  private NoteCategory category;

  public Note(Long id, String name, Long thumbnailId, NoteCategory category,
              LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
    this.thumbnailId = thumbnailId;
    this.category = category;
  }
}
