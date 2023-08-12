package io.perfume.api.sample.domain;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.jetbrains.annotations.NotNull;

@Entity(name = "sample")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sample extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("PK")
  private Long id;

  @Column(nullable = false)
  @Comment("이름")
  private String name;

  @Builder
  public Sample(Long id, @NotNull String name) {
    this.id = id;
    this.name = name;
  }

  public void changeName(String name) {
    if (name.isBlank() || name.isEmpty()) {
      throw new IllegalArgumentException();
    }

    this.name = name;
  }
}
