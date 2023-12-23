package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import java.util.List;
import lombok.Builder;

@Builder
public record PerfumeResponseDto(
    String name,
    String story,
    String strength,
    String duration,
    String perfumeShopUrl,
    String brandName,
    String categoryName,
    String categoryTags,
    String thumbnail,
    List<NoteResponseDto> topNotes,
    List<NoteResponseDto> middleNotes,
    List<NoteResponseDto> baseNotes) {

  public static PerfumeResponseDto from(PerfumeResult perfumeResult) {
    return PerfumeResponseDto.builder()
        .name(perfumeResult.name())
        .story(perfumeResult.story())
        .strength(perfumeResult.concentration().getStrength())
        .duration(perfumeResult.concentration().getDuration())
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
