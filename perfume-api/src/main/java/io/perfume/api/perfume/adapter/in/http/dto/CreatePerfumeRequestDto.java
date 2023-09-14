package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.domain.Concentration;
import java.util.List;

public record CreatePerfumeRequestDto(String name, String story, Concentration concentration, Long price, Long capacity, Long brandId,
                                      Long categoryId, Long thumbnailId, List<Long> topNoteIds, List<Long> middleNoteIds, List<Long> baseNoteIds) {
}
