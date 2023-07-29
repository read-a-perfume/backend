package io.perfume.api.note.adapter.out.persistence.noteUser;

import io.perfume.api.base.BaseTimeEntity;
import io.perfume.api.note.adapter.out.persistence.note.NoteJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
        @UniqueConstraint(name = "uni_user_id_note_id", columnNames = {"userId", "note_id"}),
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

  @ManyToOne()
  @JoinColumn(name = "note_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none"), nullable = false)
  private NoteJpaEntity note;

  @Column(nullable = false)
  private Long userId;

  public NoteUserJpaEntity(Long id, NoteJpaEntity note, Long userId, LocalDateTime createdAt,
                           LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.note = note;
    this.userId = userId;
  }

  static NoteUserJpaEntity create(NoteJpaEntity note, Long userId, LocalDateTime now) {
    return new NoteUserJpaEntity(null, note, userId, now, now, null);
  }
}
