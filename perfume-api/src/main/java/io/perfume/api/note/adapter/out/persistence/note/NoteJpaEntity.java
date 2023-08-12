package io.perfume.api.note.adapter.out.persistence.note;

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
import org.hibernate.annotations.Comment;

@Entity(name = "note")
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "uni_note_name", columnNames = "name"),
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
  @Comment("PK")
  private Long id;

  @Column(nullable = false)
  @Comment("노트 이름")
  private String name;

  @Comment("노트 설명")
  private String description;

  @Comment("Thumbnail Table FK")
  private Long thumbnailId;

  public NoteJpaEntity(Long id, String name, String description, Long thumbnailId,
                       LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
    this.description = description;
    this.thumbnailId = thumbnailId;
  }
}
