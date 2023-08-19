package io.perfume.api.review.adapter.out.persistence.entity;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "tag")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
public class TagEntity extends BaseTimeEntity {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NaturalId
  private String name;

  protected TagEntity() {
  }

  public TagEntity(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt,
                   LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
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
    TagEntity tagEntity = (TagEntity) o;
    return name != null && Objects.equals(name, tagEntity.name);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(name);
  }
}
