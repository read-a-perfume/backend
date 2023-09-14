package io.perfume.api.perfume.application.service;

import io.perfume.api.perfume.adapter.out.persistence.perfume.PerfumePersistenceAdapter;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePerfumeService implements CreatePerfumeUseCase {
  private final PerfumePersistenceAdapter perfumePersistenceAdapter;

  @Override
  public void createPerfume(CreatePerfumeCommand createPerfumeCommand) {
    Perfume perfume = Perfume.builder()
        .name(createPerfumeCommand.name())
        .story(createPerfumeCommand.story())
        .concentration(createPerfumeCommand.concentration())
        .price(createPerfumeCommand.price())
        .capacity(createPerfumeCommand.capacity())
        .brandId(createPerfumeCommand.brandId())
        .categoryId(createPerfumeCommand.categoryId())
        .thumbnailId(createPerfumeCommand.thumbnailId())
        .build();
//    NotePyramid notePyramid = NotePyramid.builder()
//        .topNoteIds(createPerfumeCommand.topNoteIds())
//        .middleNoteIds(createPerfumeCommand.middleNoteIds())
//        .baseNoteIds(createPerfumeCommand.baseNoteIds())
//        .build();

    perfumePersistenceAdapter.save(perfume);
  }
}
