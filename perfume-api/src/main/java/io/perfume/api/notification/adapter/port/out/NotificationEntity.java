package io.perfume.api.notification.adapter.port.out;

import io.perfume.api.base.BaseTimeEntity;
import io.perfume.api.notification.domain.NotificationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.TrueFalseConverter;

@Entity
@Table(name = "notification")
@Getter
public class NotificationEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  // @Column(nullable = false)
  private String redirectUrl;

  @Column(nullable = false)
  private Long receiveUserId;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  @Column(nullable = false)
  @Convert(converter = TrueFalseConverter.class)
  private Boolean isRead;

  protected NotificationEntity() {}

  public NotificationEntity(
      Long id,
      String content,
      String redirectUrl,
      Long receiveUserId,
      NotificationType notificationType,
      Boolean isRead,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.content = content;
    this.redirectUrl = redirectUrl;
    this.receiveUserId = receiveUserId;
    this.notificationType = notificationType;
    this.isRead = isRead;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    NotificationEntity that = (NotificationEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
