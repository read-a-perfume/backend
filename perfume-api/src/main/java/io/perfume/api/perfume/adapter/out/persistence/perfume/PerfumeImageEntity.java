package io.perfume.api.perfume.adapter.out.persistence.perfume;

import static lombok.AccessLevel.PROTECTED;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "perfume_image")
@Table(
    indexes = {
      @Index(name = "idx_perfume_image_1", columnList = "perfume_id"),
      @Index(name = "idx_perfume_image_2", columnList = "image_id")
    })
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PerfumeImageEntity extends BaseTimeEntity {
  @EmbeddedId private PerfumeImageId perfumeId;

  public PerfumeImageEntity(Long perfumeId, Long imageId) {
    this.perfumeId = new PerfumeImageId(perfumeId, imageId);
  }
}
