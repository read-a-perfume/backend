package io.perfume.api.perfume.application.service;

import io.perfume.api.perfume.adapter.out.persistence.perfume.PerfumePersistenceAdapter;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;
import io.perfume.api.perfume.domain.NotePyramidIds;
import io.perfume.api.perfume.domain.Perfume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePerfumeService implements CreatePerfumeUseCase {

  private final PerfumePersistenceAdapter perfumePersistenceAdapter;

  @Override
  public void createPerfume(CreatePerfumeCommand createPerfumeCommand) {
    NotePyramidIds notePyramidIds =
        NotePyramidIds.builder()
            .topNoteIds(createPerfumeCommand.topNoteIds())
            .middleNoteIds(createPerfumeCommand.middleNoteIds())
            .baseNoteIds(createPerfumeCommand.baseNoteIds())
            .build();

    Perfume perfume =
        Perfume.builder()
            .name(createPerfumeCommand.name())
            .story(createPerfumeCommand.story())
            .concentration(createPerfumeCommand.concentration())
            .brandId(createPerfumeCommand.brandId())
            .categoryId(createPerfumeCommand.categoryId())
            .thumbnailId(createPerfumeCommand.thumbnailId())
            .imageIds(createPerfumeCommand.imageIds())
            .notePyramidIds(notePyramidIds)
            .build();

    perfumePersistenceAdapter.save(perfume);
  }
}
