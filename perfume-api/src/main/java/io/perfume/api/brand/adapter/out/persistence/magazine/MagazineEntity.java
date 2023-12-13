package io.perfume.api.brand.adapter.out.persistence.magazine;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "brand_magazine")
@Getter
public class MagazineEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  private String title;

  private String subTitle;

  private String content;

  private Long coverThumbnailId;

  private Long thumbnailId;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private Long brandId;

  protected MagazineEntity() {}

  public MagazineEntity(
      Long id,
      String title,
      String subTitle,
      String content,
      Long coverThumbnailId,
      Long thumbnailId,
      Long userId,
      Long brandId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.content = content;
    this.coverThumbnailId = coverThumbnailId;
    this.thumbnailId = thumbnailId;
    this.userId = userId;
    this.brandId = brandId;
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
    MagazineEntity that = (MagazineEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" + "id = " + id + ")";
  }
}
