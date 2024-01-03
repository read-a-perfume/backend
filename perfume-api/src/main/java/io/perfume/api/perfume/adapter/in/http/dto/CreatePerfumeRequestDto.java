package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;
import io.perfume.api.perfume.domain.Concentration;
import java.util.List;

public record CreatePerfumeRequestDto(
    String name,
    String story,
    Concentration concentration,
    Long brandId,
    Long categoryId,
    Long thumbnailId,
    List<Long> imageIds,
    String perfumeShopUrl,
    List<Long> topNoteIds,
    List<Long> middleNoteIds,
    List<Long> baseNoteIds) {

  public CreatePerfumeCommand toCommand() {
    return new CreatePerfumeCommand(
        name,
        story,
        concentration,
        brandId,
        categoryId,
        thumbnailId,
        imageIds,
        perfumeShopUrl,
        topNoteIds,
        middleNoteIds,
        baseNoteIds);
  }
}
