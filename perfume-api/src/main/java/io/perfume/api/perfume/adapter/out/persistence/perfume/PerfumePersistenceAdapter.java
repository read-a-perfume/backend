package io.perfume.api.perfume.adapter.out.persistence.perfume;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteEntity;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteJpaRepository;
import io.perfume.api.perfume.application.port.out.PerfumeRepository;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
@RequiredArgsConstructor
public class PerfumePersistenceAdapter implements PerfumeRepository {
  private final PerfumeJpaRepository perfumeJpaRepository;
  private final PerfumeNoteJpaRepository perfumeNoteJpaRepository;
  private final PerfumeMapper perfumeMapper;

  @Transactional
  public Perfume save(Perfume perfume) {
    PerfumeJpaEntity perfumeJpaEntity = perfumeMapper.toPerfumeJpaEntity(perfume);
    perfumeJpaRepository.save(perfumeJpaEntity);
    List<PerfumeNoteEntity> perfumeNoteEntities =
        perfumeMapper.toPerfumeNoteEntities(perfumeJpaEntity.getId(), perfume.getNotePyramidIds());
    perfumeNoteJpaRepository.saveAll(perfumeNoteEntities);
    return perfumeMapper.toPerfume(perfumeJpaEntity, perfumeNoteEntities);
  }
}
