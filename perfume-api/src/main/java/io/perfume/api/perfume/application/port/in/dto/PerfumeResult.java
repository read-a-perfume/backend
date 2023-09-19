package io.perfume.api.perfume.application.port.in.dto;

import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import lombok.Builder;

@Builder
public record PerfumeResult(String name, String story, Concentration concentration, Long price, Long capacity, String perfumeShopUrl,
                            String brandName, String categoryName, String categoryDescription, String thumbnailUrl, List<NoteResult> topNotes,
                            List<NoteResult> middleNotes, List<NoteResult> baseNotes) {

  public static PerfumeResult from(Perfume perfume, CategoryResult categoryResult, BrandForPerfumeResult brandResult) {
    return PerfumeResult.builder()
        .name(perfume.getName())
        .story(perfume.getStory())
        .concentration(perfume.getConcentration())
        .price(perfume.getPrice())
        .capacity(perfume.getCapacity())
        .perfumeShopUrl(perfume.getPerfumeShopUrl())
        .brandName(brandResult.name())
        .categoryName(categoryResult.name())
        .categoryDescription(categoryResult.description())
        // TODO: return notes
        .build();
  }
}
