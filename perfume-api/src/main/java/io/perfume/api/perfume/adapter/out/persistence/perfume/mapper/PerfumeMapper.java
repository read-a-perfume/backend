package io.perfume.api.perfume.adapter.out.persistence.perfume.mapper;

import static io.perfume.api.note.adapter.out.persistence.note.QNoteJpaEntity.noteJpaEntity;
import static io.perfume.api.perfume.adapter.out.persistence.perfumeNote.QPerfumeNoteEntity.perfumeNoteEntity;

import com.querydsl.core.Tuple;
import io.perfume.api.perfume.adapter.out.persistence.perfume.PerfumeJpaEntity;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.NoteLevel;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteEntity;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.NotePyramidIds;
import io.perfume.api.perfume.domain.Perfume;
import io.perfume.api.perfume.domain.PerfumeNote;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class PerfumeMapper {
  public Perfume toPerfume(PerfumeJpaEntity perfumeJpaEntity) {
    return Perfume.builder()
        .id(perfumeJpaEntity.getId())
        .name(perfumeJpaEntity.getName())
        .story(perfumeJpaEntity.getStory())
        .concentration(perfumeJpaEntity.getConcentration())
        .perfumeShopUrl(perfumeJpaEntity.getPerfumeShopUrl())
        .brandId(perfumeJpaEntity.getBrandId())
        .categoryId(perfumeJpaEntity.getCategoryId())
        .thumbnailId(perfumeJpaEntity.getThumbnailId())
        .deletedAt(perfumeJpaEntity.getDeletedAt())
        .updatedAt(perfumeJpaEntity.getUpdatedAt())
        .createdAt(perfumeJpaEntity.getCreatedAt())
        .build();
  }

  public Perfume toPerfume(
      PerfumeJpaEntity perfumeJpaEntity, List<PerfumeNoteEntity> perfumeNoteEntities) {
    return Perfume.builder()
        .id(perfumeJpaEntity.getId())
        .name(perfumeJpaEntity.getName())
        .story(perfumeJpaEntity.getStory())
        .concentration(perfumeJpaEntity.getConcentration())
        .perfumeShopUrl(perfumeJpaEntity.getPerfumeShopUrl())
        .brandId(perfumeJpaEntity.getBrandId())
        .categoryId(perfumeJpaEntity.getCategoryId())
        .thumbnailId(perfumeJpaEntity.getThumbnailId())
        .notePyramidIds(toNotePyramidIds(perfumeNoteEntities))
        .deletedAt(perfumeJpaEntity.getDeletedAt())
        .updatedAt(perfumeJpaEntity.getUpdatedAt())
        .createdAt(perfumeJpaEntity.getCreatedAt())
        .build();
  }

  private NotePyramidIds toNotePyramidIds(List<PerfumeNoteEntity> perfumeNoteEntities) {
    List<Long> topNoteIds = new ArrayList<>();
    List<Long> middleNoteIds = new ArrayList<>();
    List<Long> baseNoteIds = new ArrayList<>();

    perfumeNoteEntities.forEach(
        e -> {
          switch (e.getNoteLevel()) {
            case TOP:
              topNoteIds.add(e.getNoteId());
              break;
            case MIDDLE:
              middleNoteIds.add(e.getNoteId());
              break;
            case BASE:
              baseNoteIds.add(e.getNoteId());
          }
        });

    return NotePyramidIds.builder()
        .topNoteIds(topNoteIds)
        .middleNoteIds(middleNoteIds)
        .baseNoteIds(baseNoteIds)
        .build();
  }

  public NotePyramid toNotePyramid(List<Tuple> fetchedTuples) {
    List<PerfumeNote> topPerfumeNotes = new ArrayList<>();
    List<PerfumeNote> middlePerfumeNotes = new ArrayList<>();
    List<PerfumeNote> basePerfumeNotes = new ArrayList<>();

    fetchedTuples.forEach(
        tuple -> {
          switch (Objects.requireNonNull(tuple.get(perfumeNoteEntity.noteLevel))) {
            case TOP:
              topPerfumeNotes.add(toPerfumeNote(tuple));
              break;
            case MIDDLE:
              middlePerfumeNotes.add(toPerfumeNote(tuple));
              break;
            case BASE:
              basePerfumeNotes.add(toPerfumeNote(tuple));
              break;
          }
        });
    return new NotePyramid(topPerfumeNotes, middlePerfumeNotes, basePerfumeNotes);
  }

  private PerfumeNote toPerfumeNote(Tuple tuple) {
    return new PerfumeNote(
        tuple.get(perfumeNoteEntity.noteId),
        tuple.get(noteJpaEntity.name),
        tuple.get(noteJpaEntity.thumbnailId),
        tuple.get(perfumeNoteEntity.noteLevel));
  }

  public PerfumeJpaEntity toPerfumeJpaEntity(Perfume perfume) {
    return PerfumeJpaEntity.builder()
        .id(perfume.getId())
        .name(perfume.getName())
        .story(perfume.getStory())
        .concentration(perfume.getConcentration())
        .perfumeShopUrl(perfume.getPerfumeShopUrl())
        .brandId(perfume.getBrandId())
        .categoryId(perfume.getCategoryId())
        .thumbnailId(perfume.getThumbnailId())
        .build();
  }

  public List<PerfumeNoteEntity> toPerfumeNoteEntities(
      Long perfumeId, NotePyramidIds notePyramidIds) {
    return Stream.of(
            toPerfumeNoteEntities(notePyramidIds.getTopNoteIds(), perfumeId, NoteLevel.TOP),
            toPerfumeNoteEntities(notePyramidIds.getMiddleNoteIds(), perfumeId, NoteLevel.MIDDLE),
            toPerfumeNoteEntities(notePyramidIds.getBaseNoteIds(), perfumeId, NoteLevel.BASE))
        .flatMap(List::stream)
        .toList();
  }

  private List<PerfumeNoteEntity> toPerfumeNoteEntities(
      List<Long> noteIds, Long perfumeId, NoteLevel noteLevel) {
    return noteIds.stream().map(toPerfumeNoteEntity(perfumeId, noteLevel)).toList();
  }

  private Function<Long, PerfumeNoteEntity> toPerfumeNoteEntity(
      Long perfumeId, NoteLevel noteLevel) {
    return noteId ->
        PerfumeNoteEntity.builder()
            .perfumeId(perfumeId)
            .noteId(noteId)
            .noteLevel(noteLevel)
            .build();
  }
}
