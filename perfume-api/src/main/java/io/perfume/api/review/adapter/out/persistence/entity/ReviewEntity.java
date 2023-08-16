package io.perfume.api.review.adapter.out.persistence.entity;

import io.perfume.api.base.BaseTimeEntity;
import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "review")
@Getter
public class ReviewEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  private String feeling;

  private String situation;

  private STRENGTH strength;

  private Long duration;

  private SEASON season;

  @OneToMany(
      mappedBy = "review",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<ReviewTagEntity> tags = new ArrayList<>();

  @Column(nullable = false)
  private Long perfumeId;

  @Column(nullable = false)
  private Long userId;

  protected ReviewEntity() {
  }

  public ReviewEntity(Long id, String feeling, String situation, STRENGTH strength, Long duration,
                      SEASON season, Long perfumeId, Long userId, LocalDateTime createdAt,
                      LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.feeling = feeling;
    this.situation = situation;
    this.strength = strength;
    this.duration = duration;
    this.season = season;
    this.perfumeId = perfumeId;
    this.userId = userId;
  }

  public void addTag(TagEntity tag) {
    ReviewTagEntity reviewTag = new ReviewTagEntity(this, tag);
    tags.add(reviewTag);
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
    ReviewEntity that = (ReviewEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ?
        ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
        getClass().hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
        "id = " + id + ")";
  }
}
