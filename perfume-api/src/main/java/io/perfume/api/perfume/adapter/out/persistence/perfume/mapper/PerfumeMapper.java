package io.perfume.api.perfume.adapter.out.persistence.perfume.mapper;

import io.perfume.api.note.domain.Note;
import io.perfume.api.perfume.adapter.out.persistence.perfume.PerfumeJpaEntity;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.NoteLevel;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteEntity;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PerfumeMapper {
  public Perfume toPerfume(PerfumeJpaEntity perfumeJpaEntity) {
    return Perfume.builder()
        .id(perfumeJpaEntity.getId())
        .name(perfumeJpaEntity.getName())
        .story(perfumeJpaEntity.getStory())
        .price(perfumeJpaEntity.getPrice())
        .concentration(perfumeJpaEntity.getConcentration())
        .capacity(perfumeJpaEntity.getCapacity())
        .brandId(perfumeJpaEntity.getBrandId())
        .deletedAt(perfumeJpaEntity.getDeletedAt())
        .updatedAt(perfumeJpaEntity.getUpdatedAt())
        .createdAt(perfumeJpaEntity.getCreatedAt())
        .build();
  }

  public PerfumeJpaEntity toPerfumeJpaEntity(Perfume perfume) {
    return PerfumeJpaEntity.builder()
        .id(perfume.getId())
        .name(perfume.getName())
        .story(perfume.getStory())
        .concentration(perfume.getConcentration())
        .price(perfume.getPrice())
        .capacity(perfume.getCapacity())
        .brandId(perfume.getBrandId())
        .thumbnailId(perfume.getThumbnailId())
        .build();
  }

  public List<PerfumeNoteEntity> toPerfumeNoteEntities(Long perfumeId, NotePyramid notePyramid) {
    List<PerfumeNoteEntity> perfumeNoteEntities = new ArrayList<>();
    for (Note note : notePyramid.getTopNotes()) {
      perfumeNoteEntities.add(PerfumeNoteEntity.builder()
          .perfumeId(perfumeId)
          .noteId(note.getId())
          .noteLevel(NoteLevel.TOP)
          .build());
    }
    for (Note note : notePyramid.getMiddleNotes()) {
      perfumeNoteEntities.add(PerfumeNoteEntity.builder()
          .perfumeId(perfumeId)
          .noteId(note.getId())
          .noteLevel(NoteLevel.MIDDLE)
          .build());
    }
    for (Note note : notePyramid.getBaseNotes()) {
      perfumeNoteEntities.add(PerfumeNoteEntity.builder()
          .perfumeId(perfumeId)
          .noteId(note.getId())
          .noteLevel(NoteLevel.BASE)
          .build());
    }
    return perfumeNoteEntities;
  }
}
