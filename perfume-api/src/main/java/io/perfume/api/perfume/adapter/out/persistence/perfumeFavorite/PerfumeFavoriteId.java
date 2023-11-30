package io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;

@Embeddable
@Getter
public class PerfumeFavoriteId implements Serializable {

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "perfume_id")
  private Long perfumeId;

  protected PerfumeFavoriteId() {
  }

  public PerfumeFavoriteId(Long userId, Long perfumeId) {
    this.userId = userId;
    this.perfumeId = perfumeId;
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
    PerfumeFavoriteId that = (PerfumeFavoriteId) o;
    return userId != null && Objects.equals(userId, that.userId)
        && perfumeId != null && Objects.equals(perfumeId, that.perfumeId);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(userId, perfumeId);
  }
}
