package io.perfume.api.perfume.application.port.in.dto;

import io.perfume.api.perfume.domain.Concentration;
import java.util.List;

public record CreatePerfumeCommand(
    String name,
    String story,
    Concentration concentration,
    Long brandId,
    Long categoryId,
    Long thumbnailId,
    String perfumeShopUrl,
    List<Long> topNoteIds,
    List<Long> middleNoteIds,
    List<Long> baseNoteIds) {}
