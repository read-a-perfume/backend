package io.perfume.api.perfume.adapter.out.persistence.perfume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.perfume.domain.Perfume;
import jakarta.persistence.EntityManager;
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
    Perfume resultPerfume = perfumeQueryPersistenceAdapter.findPerfumeById(perfumeJpaEntity.getId());
    // then
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
}
