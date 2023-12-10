package io.perfume.api.review.adapter.out.persistence.repository.tag;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;

@Embeddable
@Getter
public class ReviewTagId implements Serializable {

  @Column(name = "review_id")
  private Long reviewId;

  @Column(name = "tag_id")
  private Long tagId;

  protected ReviewTagId() {}

  public ReviewTagId(Long reviewId, Long tagId) {
    this.reviewId = reviewId;
    this.tagId = tagId;
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
    ReviewTagId that = (ReviewTagId) o;
    return reviewId != null
        && Objects.equals(reviewId, that.reviewId)
        && tagId != null
        && Objects.equals(tagId, that.tagId);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(reviewId, tagId);
  }
}
