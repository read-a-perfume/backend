package io.perfume.api.brand.adapter.out.persistence.tag;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;

@Embeddable
@Getter
public class MagazineTagId implements Serializable {

  @Column(name = "magazine_id")
  private Long magazineId;

  @Column(name = "tag_id")
  private Long tagId;

  protected MagazineTagId() {}

  public MagazineTagId(Long magazineId, Long tagId) {
    this.magazineId = magazineId;
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
    MagazineTagId that = (MagazineTagId) o;
    return magazineId != null
        && Objects.equals(magazineId, that.magazineId)
        && tagId != null
        && Objects.equals(tagId, that.tagId);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(magazineId, tagId);
  }
}
