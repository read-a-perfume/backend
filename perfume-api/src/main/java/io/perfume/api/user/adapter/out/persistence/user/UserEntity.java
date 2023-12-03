package io.perfume.api.user.adapter.out.persistence.user;

import io.perfume.api.base.BaseTimeEntity;
import io.perfume.api.user.domain.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;

@Entity(name = "member")
@Table(
    indexes = {
        @Index(name = "idx_business_id", columnList = "businessId"),
        @Index(name = "idx_thumbnail_id", columnList = "thumbnailId")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uni_email", columnNames = "email"),
        @UniqueConstraint(name = "uni_username", columnNames = "username"),
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class UserEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ToString.Include
  private Long id;

  @NotNull
  @Column(updatable = false)
  @Comment("사용자 아이디")
  private String username;

  @NotBlank(message = "공백(스페이스 바)을 허용하지 않습니다.")
  @Email
  @Comment("사용자 이메일")
  private String email;

  @NotBlank(message = "공백(스페이스 바)을 허용하지 않습니다.")
  @Comment("사용자 비밀번호")
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Comment("사용자 권한")
  @Column(nullable = false)
  private Role role;

  @Comment("사용자 소개")
  private String bio;

  @Comment("사용자 생일")
  private LocalDate birthday;

  @Enumerated(EnumType.STRING)
  @Comment("사용자 성별")
  @Column(nullable = false)
  private Sex sex = Sex.OTHER;

  @NotNull
  @Comment("마케팅 동의 여부")
  @Column(nullable = false)
  private Boolean marketingConsent = false;

  @NotNull
  @Comment("프로모션 동의 여부")
  @Column(nullable = false)
  private Boolean promotionConsent = false;

  private Long businessId;

  private Long thumbnailId;

  @Builder
  public UserEntity(Long id, String username, String email, String password, Role role, String bio, LocalDate birthday, Sex sex, Boolean marketingConsent, Boolean promotionConsent, Long thumbnailId,
                    LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.bio = bio;
    this.birthday = birthday;
    this.sex = sex;
    this.marketingConsent = marketingConsent;
    this.promotionConsent = promotionConsent;
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
    Class<?> oEffectiveClass = o instanceof HibernateProxy ?
        ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() :
        o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
        ((HibernateProxy) this).getHibernateLazyInitializer()
            .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    UserEntity that = (UserEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ?
        ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
        getClass().hashCode();
  }
}
