package io.perfume.api.note.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class NoteUser extends BaseTimeDomain {

  private final Long id;

  private final Long userId;

  private final Long noteId;

  public NoteUser(Long id, Long userId, Long noteId, LocalDateTime createdAt,
                  LocalDateTime updatedAt,
                  LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.userId = userId;
    this.noteId = noteId;
  }

  public static NoteUser create(Long userId, Long noteId, LocalDateTime now) {
    return new NoteUser(null, userId, noteId, now, now, null);
  }
}
