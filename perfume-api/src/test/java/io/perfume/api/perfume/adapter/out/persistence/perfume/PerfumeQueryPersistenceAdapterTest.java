package io.perfume.api.perfume.adapter.out.persistence.perfume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.note.adapter.out.persistence.note.NoteJpaEntity;
import io.perfume.api.note.adapter.out.persistence.note.NoteJpaRepository;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.NoteLevel;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteEntity;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({PerfumeQueryPersistenceAdapter.class, PerfumeMapper.class, TestQueryDSLConfiguration.class})
@DataJpaTest
@EnableJpaAuditing
class PerfumeQueryPersistenceAdapterTest {
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private PerfumeQueryPersistenceAdapter perfumeQueryPersistenceAdapter;
  @Autowired
  private PerfumeJpaRepository perfumeJpaRepository;
  @Autowired
  private NoteJpaRepository noteJpaRepository;
  @Autowired
  private PerfumeMapper perfumeMapper;

  @Test
  void findPerfumeById() {
    // given
    Perfume perfume = Perfume.builder()
        .name("테싯 오 드 퍼퓸")
        .story("마음을 차분하게 가라앉혀주고 우리 몸의 감각을 일깨워주는 흙내음과 시트러스 노트의 따뜻하고 생기 넘치는 블렌드")
        .concentration(Concentration.EAU_DE_PERFUME)
        .price(150000L)
        .capacity(50L)
        .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
        .brandId(1L)
        .categoryId(1L)
        .thumbnailId(1L)
        .build();

    PerfumeJpaEntity perfumeJpaEntity = perfumeJpaRepository.save(perfumeMapper.toPerfumeJpaEntity(perfume));
    entityManager.clear();
    // when
    Optional<Perfume> optionalPerfume = perfumeQueryPersistenceAdapter.findPerfumeById(perfumeJpaEntity.getId());
    Perfume resultPerfume = optionalPerfume.get();

    // then
    assertNotNull(optionalPerfume.isPresent());
    assertEquals(perfume.getName(), resultPerfume.getName());
    assertEquals(perfume.getStory(), resultPerfume.getStory());
    assertEquals(perfume.getConcentration(), resultPerfume.getConcentration());
    assertEquals(perfume.getPrice(), resultPerfume.getPrice());
    assertEquals(perfume.getCapacity(), resultPerfume.getCapacity());
    assertEquals(perfume.getPerfumeShopUrl(), resultPerfume.getPerfumeShopUrl());
    assertNotNull(resultPerfume.getCreatedAt());
    assertNotNull(resultPerfume.getUpdatedAt());
    assertNull(resultPerfume.getDeletedAt());
  }

  @Test
  void getNotePyramidIdsByPerfume() {
    // given
    Perfume perfume = Perfume.builder()
        .name("테싯 오 드 퍼퓸")
        .story("마음을 차분하게 가라앉혀주고 우리 몸의 감각을 일깨워주는 흙내음과 시트러스 노트의 따뜻하고 생기 넘치는 블렌드")
        .concentration(Concentration.EAU_DE_PERFUME)
        .price(150000L)
        .capacity(50L)
        .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
        .brandId(1L)
        .categoryId(1L)
        .thumbnailId(1L)
        .build();

    PerfumeJpaEntity perfumeJpaEntity = perfumeMapper.toPerfumeJpaEntity(perfume);
    entityManager.persist(perfumeJpaEntity);

    NoteJpaEntity noteJpaEntity1 = NoteJpaEntity.builder().name("핑크 페퍼").description("핑크페퍼 향").thumbnailId(1L).build();
    NoteJpaEntity noteJpaEntity2 = NoteJpaEntity.builder().name("머스크").description("머스크 향").thumbnailId(2L).build();
    Iterable<NoteJpaEntity> noteJpaEntities = noteJpaRepository.saveAll(List.of(noteJpaEntity1, noteJpaEntity2));

    noteJpaEntities.forEach(entity -> entityManager.persist(PerfumeNoteEntity.builder()
        .perfumeId(perfumeJpaEntity.getId())
        .noteId(entity.getId())
        .noteLevel(NoteLevel.TOP)
        .build()));

    entityManager.clear();
    // when
    NotePyramid notePyramidIdsByPerfume = perfumeQueryPersistenceAdapter.getNotePyramidByPerfume(1L);
    // then
    assertNotNull(notePyramidIdsByPerfume);
  }
}
