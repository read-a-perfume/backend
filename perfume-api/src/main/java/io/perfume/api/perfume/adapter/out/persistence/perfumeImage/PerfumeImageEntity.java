package io.perfume.api.perfume.adapter.out.persistence.perfumeImage;

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
  @EmbeddedId private PerfumeImageId perfumeImageId;

  public PerfumeImageEntity(Long perfumeId, Long imageId) {
    this.perfumeImageId = new PerfumeImageId(perfumeId, imageId);
  }
}
