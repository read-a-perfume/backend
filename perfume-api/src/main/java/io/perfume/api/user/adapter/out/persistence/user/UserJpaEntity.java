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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "users")
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class UserJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Long id;

  @NotNull
  @Column(updatable = false)
  private String username;

  @NotNull
  @Email      // 제약조건 설정?  --> @Email "" 는 통과
  @Column(updatable = false)
  private String email;

  @NotBlank(message = "공백(스페이스 바)을 허용하지 않습니다.")
  private String password;

  @NotEmpty
  @Column(updatable = false)
  private String name;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Role role;

  @NotNull
  private Boolean marketingConsent = false;

  @NotNull
  private Boolean promotionConsent = false;

  @NotNull
  private Boolean isOauth = false;

  private LocalDate birth;

  private String bio;

  private Long businessId;

  private Long thumbnailId;


  // Mapper Library 필요
  @Builder(access = AccessLevel.PACKAGE)
  public UserJpaEntity(Long id, String username, String email, String password,
                       String name, Role role, Boolean marketingConsent,
                       Boolean promotionConsent, LocalDateTime createdAt, LocalDateTime updatedAt,
                       LocalDate birth, LocalDateTime deletedAt, Boolean isOauth, String bio) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.name = name;
    this.role = role;
    this.marketingConsent = marketingConsent;
    this.promotionConsent = promotionConsent;
    this.birth = birth;
    this.isOauth = isOauth;
    this.bio = bio;
  }
}
