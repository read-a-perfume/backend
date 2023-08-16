package io.perfume.api.review.adapter.out.persistence.entity;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "review_tag")
@Getter
public class ReviewTagEntity extends BaseTimeEntity {

  @EmbeddedId
  private ReviewTagId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("reviewId")
  private ReviewEntity review;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("tagId")
  private TagEntity tag;

  protected ReviewTagEntity() {
  }

  public ReviewTagEntity(ReviewEntity review, TagEntity tag) {
    this.review = review;
    this.tag = tag;
    this.id = new ReviewTagId(review.getId(), tag.getId());
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass = o instanceof HibernateProxy ?
        ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() :
        o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
        ((HibernateProxy) this).getHibernateLazyInitializer()
            .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    ReviewTagEntity that = (ReviewTagEntity) o;
    return review != null && Objects.equals(review, that.review) && tag != null
        && Objects.equals(tag, that.tag);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(id);
  }
}
