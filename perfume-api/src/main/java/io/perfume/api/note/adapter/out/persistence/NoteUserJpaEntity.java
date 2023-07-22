package io.perfume.api.note.adapter.out.persistence;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "note_user")
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "uni_user_id_note_id", columnNames = {"userId", "noteId"}),
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class NoteUserJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Long id;

  @Column(nullable = false)
  private Long noteId;

  @Column(nullable = false)
  private Long userId;

  public NoteUserJpaEntity(Long id, Long noteId, Long userId, LocalDateTime createdAt,
                           LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.noteId = noteId;
    this.userId = userId;
  }

  static NoteUserJpaEntity create(Long noteId, Long userId, LocalDateTime now) {
    return new NoteUserJpaEntity(null, noteId, userId, now, now, null);
  }
}
