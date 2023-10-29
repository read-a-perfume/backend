package io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "perfume_favorite")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PerfumeFavoriteJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "perfume_id")
  private Long perfumeId;

  public PerfumeFavoriteJpaEntity(Long id, Long userId, Long perfumeId,
      LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.userId = userId;
    this.perfumeId = perfumeId;
  }
}
