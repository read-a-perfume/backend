package io.perfume.api.auth.adapter.out.persistence;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity(name = "authentication_key")
@Table(name = "authentication_key")
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

    @Column(updatable = false, nullable = false)
    private String code;

    @Column(updatable = false, nullable = false)
    private String signKey;

    private LocalDateTime verifiedAt;

    public AuthenticationKeyJpaEntity(Long id, @NotNull String code, @NotNull String key, LocalDateTime verifiedAt, @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt, LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.code = code;
        this.signKey = key;
        this.verifiedAt = verifiedAt;
    }
}
