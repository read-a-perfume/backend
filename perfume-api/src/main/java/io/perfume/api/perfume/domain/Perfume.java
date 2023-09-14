package io.perfume.api.perfume.domain;

import io.perfume.api.base.BaseTimeDomain;
import io.perfume.api.note.domain.Note;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Perfume extends BaseTimeDomain {

  private Long id;
  private String name;
  private String story;
  private Concentration concentration;
  private Long price;
  private Long capacity;
  private Long brandId;
  private Long categoryId;
  private Long thumbnailId;
  private NotePyramid notePyramid;

  @Builder
  public Perfume(Long id, String name, String story, Concentration concentration, Long price, Long capacity, Long brandId, Long thumbnailId,
                 Long categoryId, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
    this.story = story;
    this.concentration = concentration;
    this.price = price;
    this.capacity = capacity;
    this.brandId = brandId;
    this.categoryId = categoryId;
    this.thumbnailId = thumbnailId;
  }
}
