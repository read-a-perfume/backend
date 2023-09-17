package io.perfume.api.perfume.adapter.out.persistence.perfume;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteEntity;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteJpaRepository;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
public class PerfumePersistenceAdapter {
  private PerfumeJpaRepository perfumeJpaRepository;
  private PerfumeNoteJpaRepository perfumeNoteJpaRepository;
  private PerfumeMapper perfumeMapper;
  @Transactional
  public Perfume save(Perfume perfume) {
    PerfumeJpaEntity perfumeJpaEntity = perfumeMapper.toPerfumeJpaEntity(perfume);
    PerfumeJpaEntity savedEntity = perfumeJpaRepository.save(perfumeJpaEntity);
    List<PerfumeNoteEntity> perfumeNoteEntities = perfumeMapper.toPerfumeNoteEntities(
        savedEntity.getId(),
        perfume.getNotePyramidIds());
    perfumeNoteJpaRepository.saveAll(perfumeNoteEntities);
    return perfumeMapper.toPerfume(savedEntity);
  }
}
