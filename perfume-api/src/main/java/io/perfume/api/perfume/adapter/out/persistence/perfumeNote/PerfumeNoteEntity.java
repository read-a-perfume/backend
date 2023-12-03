package io.perfume.api.perfume.adapter.out.persistence.perfumeNote;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "perfume_note",
    indexes = {
      @Index(name = "idx_perfume_note_1", columnList = "perfumeId"),
      @Index(name = "idx_perfume_note_2", columnList = "noteId")
    })
@NoArgsConstructor
@Getter
public class PerfumeNoteEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull private Long perfumeId;

  @NotNull private Long noteId;

  @Enumerated(EnumType.STRING)
  @NotNull
  private NoteLevel noteLevel;

  @Builder
  public PerfumeNoteEntity(Long id, Long perfumeId, Long noteId, NoteLevel noteLevel) {
    this.id = id;
    this.perfumeId = perfumeId;
    this.noteId = noteId;
    this.noteLevel = noteLevel;
  }
}
