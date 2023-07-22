package io.perfume.api.note.adapter.out.persistence;

import io.perfume.api.base.BaseTimeEntity;
import io.perfume.api.note.domain.NoteCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "note")
@Table(
    indexes = {
        @Index(name = "idx_category", columnList = "category"),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uni_name", columnNames = "name"),
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class NoteJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Long id;

  @Column(nullable = false)
  private String name;

  private Long thumbnailId;

  @Enumerated(EnumType.STRING)
  private NoteCategory category;

  public NoteJpaEntity(Long id, String name, Long thumbnailId, NoteCategory category,
                       LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
    this.thumbnailId = thumbnailId;
    this.category = category;
  }
}
