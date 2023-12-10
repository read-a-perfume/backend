package io.perfume.api.review.adapter.out.persistence.repository.thumbnail;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ReviewThumbnailId implements Serializable {

  @Column(name = "review_id")
  private Long reviewId;

  @Column(name = "thumbnail_id")
  private Long thumbnailId;

  public ReviewThumbnailId(final Long reviewId, final Long thumbnailId) {
    this.reviewId = reviewId;
    this.thumbnailId = thumbnailId;
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
    ReviewThumbnailId that = (ReviewThumbnailId) o;
    return getReviewId() != null
        && Objects.equals(getReviewId(), that.getReviewId())
        && getThumbnailId() != null
        && Objects.equals(getThumbnailId(), that.getThumbnailId());
  }

  @Override
  public final int hashCode() {
    return Objects.hash(reviewId, thumbnailId);
  }
}
