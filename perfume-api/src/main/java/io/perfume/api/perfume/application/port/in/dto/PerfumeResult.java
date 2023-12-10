package io.perfume.api.perfume.application.port.in.dto;

import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import lombok.Builder;

@Builder
public record PerfumeResult(
    String name,
    String story,
    Concentration concentration,
    String perfumeShopUrl,
    String brandName,
    String categoryName,
    String categoryTags,
    String thumbnail,
    NotePyramidResult notePyramidResult) {

  public static PerfumeResult from(
      Perfume perfume,
      CategoryResult categoryResult,
      BrandForPerfumeResult brandResult,
      String thumbnail,
      NotePyramid notePyramid) {
    return PerfumeResult.builder()
        .name(perfume.getName())
        .story(perfume.getStory())
        .concentration(perfume.getConcentration())
        .perfumeShopUrl(perfume.getPerfumeShopUrl())
        .brandName(brandResult.name())
        .categoryName(categoryResult.name())
        .categoryTags(categoryResult.tags())
        .thumbnail(thumbnail)
        .notePyramidResult(NotePyramidResult.from(notePyramid))
        .build();
  }
}
