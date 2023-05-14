package io.perfume.api.auth.adapter.out.persistence.persistence;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity(name = "authentication_key")
@Table(name = "authentication_key", indexes = {
        @Index(name = "idx_authentication_key_user_id", columnList = "userId"),
})
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class AuthenticationKeyJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(updatable = false)
    private Long userId;

    @Column(updatable = false)
    private String code;

    @Column(updatable = false)
    private String key;

    private LocalDateTime verifiedAt;

    public AuthenticationKeyJpaEntity(Long id, Long userId, String code, String key, LocalDateTime verifiedAt, @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt, @NotNull LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.key = key;
        this.verifiedAt = verifiedAt;
    }
}
