package io.perfume.api.review.adapter.out.persistence.repository.thumbnail;

import static lombok.AccessLevel.PROTECTED;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "review_thumbnail")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ReviewThumbnailEntity extends BaseTimeEntity {

  @EmbeddedId private ReviewThumbnailId id;

  public ReviewThumbnailEntity(
      final Long reviewId,
      final Long thumbnailId,
      final LocalDateTime createdAt,
      final LocalDateTime updatedAt,
      final LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = new ReviewThumbnailId(reviewId, thumbnailId);
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    ReviewThumbnailEntity that = (ReviewThumbnailEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return Objects.hash(id);
  }
}
