package io.perfume.api.user.adapter.out.persistence;

import io.perfume.api.base.BaseTimeEntity;
import io.perfume.api.user.domain.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "member")
@Table(
        name = "member",
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
class UserJpaEntity extends BaseTimeEntity {

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

    private Long businessId;

    private Long thumbnailId;

    // Mapper Library 필요
    @Builder(access = AccessLevel.PACKAGE)
    private UserJpaEntity(Long id, String username, String email, String password,
                          String name, Role role, Boolean marketingConsent,
                          Boolean promotionConsent, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
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
