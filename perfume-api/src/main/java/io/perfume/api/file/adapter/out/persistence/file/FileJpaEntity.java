package io.perfume.api.file.adapter.out.persistence.file;

import io.perfume.api.base.BaseTimeEntity;
import io.perfume.api.user.adapter.out.persistence.user.UserJpaEntity;
import io.perfume.api.user.domain.SocialProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class FileJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Long id;

  @NotNull
  private String url;

  @NotNull
  private Long userId;

  public FileJpaEntity(Long id, String url, Long userId,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt,
                                LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);

    this.id = id;
    this.url = url;
    this.userId = userId;
  }
}
