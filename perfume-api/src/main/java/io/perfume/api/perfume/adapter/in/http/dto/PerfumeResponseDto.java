package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import io.perfume.api.perfume.domain.Concentration;
import java.util.List;
import lombok.Builder;

@Builder
public record PerfumeResponseDto(String name, String story, Concentration concentration,
                                 String perfumeShopUrl, String brandName, String categoryName, String categoryTags,
                                 String thumbnail, List<NoteResponseDto> topNotes, List<NoteResponseDto> middleNotes,
                                 List<NoteResponseDto> baseNotes) {

  public static PerfumeResponseDto of(PerfumeResult perfumeResult) {
    return PerfumeResponseDto.builder()
        .name(perfumeResult.name())
        .story(perfumeResult.story())
        .concentration(perfumeResult.concentration())
        .perfumeShopUrl(perfumeResult.perfumeShopUrl())
        .categoryName(perfumeResult.categoryName())
        .categoryTags(perfumeResult.categoryTags())
        .brandName(perfumeResult.brandName())
        .thumbnail(perfumeResult.thumbnail())
        .topNotes(NoteResponseDto.of(perfumeResult.notePyramidResult().topNotes()))
        .middleNotes(NoteResponseDto.of(perfumeResult.notePyramidResult().middleNotes()))
        .baseNotes(NoteResponseDto.of(perfumeResult.notePyramidResult().baseNotes()))
        .build();
  }
}
