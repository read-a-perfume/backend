package io.perfume.api.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class BaseTimeEntity {
  @CreatedDate
  @Column(nullable = false, updatable = false)
  @Comment("생성 날짜")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  @Comment("마지막 업데이트 날짜")
  private LocalDateTime updatedAt;

  @Comment("삭제 날짜")
  private LocalDateTime deletedAt;

  protected BaseTimeEntity(@NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt,
                           @NotNull LocalDateTime deletedAt) {
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }
}
