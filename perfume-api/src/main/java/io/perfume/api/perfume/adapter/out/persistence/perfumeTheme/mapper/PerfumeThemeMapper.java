package io.perfume.api.perfume.adapter.out.persistence.perfumeTheme.mapper;

import io.perfume.api.perfume.adapter.out.persistence.perfumeTheme.PerfumeThemeEntity;
import io.perfume.api.perfume.domain.PerfumeTheme;
import java.util.Arrays;
import java.util.List;

public class PerfumeThemeMapper {
  private PerfumeThemeMapper() {}

  public static PerfumeTheme fromEntity(PerfumeThemeEntity entity) {
    if (entity == null) {
      return null;
    }

    List<Long> perfumeIds =
        Arrays.stream(entity.getPerfumeIds().split(",")).map(Long::parseLong).toList();

    return PerfumeTheme.builder()
        .title(entity.getTitle())
        .content(entity.getContent())
        .thumbnailId(entity.getThumbnailId())
        .perfumeIds(perfumeIds)
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .deletedAt(entity.getDeletedAt())
        .build();
  }
}
