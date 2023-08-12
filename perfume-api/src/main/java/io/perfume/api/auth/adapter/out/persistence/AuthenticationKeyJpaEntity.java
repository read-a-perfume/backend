package io.perfume.api.auth.adapter.out.persistence;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/*
  TODO
    1. @Comment 작업하기
 */

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

  public AuthenticationKeyJpaEntity(Long id, @NotNull String code, @NotNull String key,
                                    LocalDateTime verifiedAt, @NotNull LocalDateTime createdAt,
                                    @NotNull LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.code = code;
    this.signKey = key;
    this.verifiedAt = verifiedAt;
  }
}
