package io.perfume.api.perfume.adapter.out.persistence.perfumeNote;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PerfumeNoteEntity extends BaseTimeEntity {
  @Id
  private Long id;
  private Long perfumeId;
  private Long noteId;
  private NoteLevel noteLevel;

  @Builder
  public PerfumeNoteEntity(Long id, Long perfumeId, Long noteId, NoteLevel noteLevel) {
    this.id = id;
    this.perfumeId = perfumeId;
    this.noteId = noteId;
    this.noteLevel = noteLevel;
  }
}
