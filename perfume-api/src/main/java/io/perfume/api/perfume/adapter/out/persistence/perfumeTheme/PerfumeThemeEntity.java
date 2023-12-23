package io.perfume.api.perfume.adapter.out.persistence.perfumeTheme;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "perfume_theme")
@Getter
@NoArgsConstructor
public class PerfumeThemeEntity extends BaseTimeEntity {
  @Id private Long id;
  private String title;
  private String content;
  private Long thumbnailId;
  private String perfumeIds;

  public PerfumeThemeEntity(
      Long id, String title, String content, Long thumbnailId, String perfumeIds) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.thumbnailId = thumbnailId;
    this.perfumeIds = perfumeIds;
  }
}
