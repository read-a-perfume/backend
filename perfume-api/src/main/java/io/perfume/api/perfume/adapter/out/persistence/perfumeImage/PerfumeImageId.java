package io.perfume.api.perfume.adapter.out.persistence.perfumeImage;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class PerfumeImageId implements Serializable {
  private Long perfumeId;
  private Long imageId;

  public PerfumeImageId(Long perfumeId, Long imageId) {
    this.perfumeId = perfumeId;
    this.imageId = imageId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PerfumeImageId that = (PerfumeImageId) o;
    return Objects.equals(perfumeId, that.perfumeId) && Objects.equals(imageId, that.imageId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(perfumeId, imageId);
  }
}
