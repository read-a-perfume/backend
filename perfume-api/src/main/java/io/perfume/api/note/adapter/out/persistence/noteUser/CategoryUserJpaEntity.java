package io.perfume.api.note.adapter.out.persistence.noteUser;

import io.perfume.api.base.BaseTimeEntity;
import io.perfume.api.note.adapter.out.persistence.category.CategoryJpaEntity;
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

@Entity(name = "category_user")
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "uni_user_id_note_id", columnNames = {"userId", "note_id"}),
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class CategoryUserJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Long id;

  @ManyToOne()
  @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none"), nullable = false)
  private CategoryJpaEntity category;

  @Column(nullable = false)
  private Long userId;

  public CategoryUserJpaEntity(Long id, CategoryJpaEntity category, Long userId,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.category = category;
    this.userId = userId;
  }

  static CategoryUserJpaEntity create(CategoryJpaEntity category, Long userId, LocalDateTime now) {
    return new CategoryUserJpaEntity(null, category, userId, now, now, null);
  }
}
