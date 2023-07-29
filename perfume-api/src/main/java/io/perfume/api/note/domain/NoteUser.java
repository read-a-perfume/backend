package io.perfume.api.note.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class NoteUser extends BaseTimeDomain {

  private final Long id;

  private final Long userId;

  private final Note note;

  public NoteUser(Long id, Long userId, Note note, LocalDateTime createdAt,
                  LocalDateTime updatedAt,
                  LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.userId = userId;
    this.note = note;
  }

  public static NoteUser create(Long userId, Note note, LocalDateTime now) {
    return new NoteUser(null, userId, note, now, now, null);
  }
}
