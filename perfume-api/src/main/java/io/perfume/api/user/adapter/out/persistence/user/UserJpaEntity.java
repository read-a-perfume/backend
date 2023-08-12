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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class  UserJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  @Comment("PK")
  private Long id;

  @NotNull
  @Column(updatable = false)
  @Comment("아이디, oauth가입이면 이메일 @ 앞부분 + unixTime 사용")
  private String username;

  @NotNull
  @Email      // 제약조건 설정?  --> @Email "" 는 통과
  @Column(updatable = false)
  @Comment("가입에 사용된 이메일")
  private String email;

  @NotBlank(message = "공백(스페이스 바)을 허용하지 않습니다.")
  @Comment("비밀번호")
  private String password;

  @NotEmpty
  @Column(updatable = false)
  @Comment("이름")
  private String name;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Comment("역할")
  private Role role;

  @NotNull
  @Comment("마케팅 사용 동의 여부")
  private Boolean marketingConsent = false;

  @NotNull
  @Comment("프로모션 사용 동의 여부") // TODO 뭐라고 쓰지?
  private Boolean promotionConsent = false;

  @Comment("Business Table FK")
  private Long businessId;

  @Comment("Thumbnail Table FK")
  private Long thumbnailId;

  // Mapper Library 필요
  @Builder(access = AccessLevel.PACKAGE)
  public UserJpaEntity(Long id, String username, String email, String password,
                       String name, Role role, Boolean marketingConsent,
                       Boolean promotionConsent, LocalDateTime createdAt, LocalDateTime updatedAt,
                       LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.name = name;
    this.role = role;
    this.marketingConsent = marketingConsent;
    this.promotionConsent = promotionConsent;
  }
}
