package io.perfume.api.perfume.adapter.out.persistence.perfume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.perfume.api.brand.adapter.out.persistence.brand.BrandEntity;
import io.perfume.api.common.page.CustomPage;
import io.perfume.api.common.page.CustomSlice;
import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.note.adapter.out.persistence.category.CategoryJpaEntity;
import io.perfume.api.note.adapter.out.persistence.note.NoteJpaEntity;
import io.perfume.api.note.adapter.out.persistence.note.NoteJpaRepository;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite.PerfumeFavoriteJpaEntity;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.NoteLevel;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteEntity;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({
  PerfumeQueryPersistenceAdapter.class,
  PerfumeMapper.class,
  TestQueryDSLConfiguration.class
})
@DataJpaTest
@EnableJpaAuditing
class PerfumeQueryPersistenceAdapterTest {
  @Autowired private EntityManager entityManager;
  @Autowired private PerfumeQueryPersistenceAdapter perfumeQueryPersistenceAdapter;
  @Autowired private PerfumeJpaRepository perfumeJpaRepository;
  @Autowired private NoteJpaRepository noteJpaRepository;
  @Autowired private PerfumeMapper perfumeMapper;

  @Test
  void findPerfumeById() {
    // given
    Perfume perfume =
        Perfume.builder()
            .name("테싯 오 드 퍼퓸")
            .story("마음을 차분하게 가라앉혀주고 우리 몸의 감각을 일깨워주는 흙내음과 시트러스 노트의 따뜻하고 생기 넘치는 블렌드")
            .concentration(Concentration.EAU_DE_PARFUM)
            .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
            .brandId(1L)
            .categoryId(1L)
            .thumbnailId(1L)
            .build();

    PerfumeJpaEntity perfumeJpaEntity =
        perfumeJpaRepository.save(perfumeMapper.toPerfumeJpaEntity(perfume));
    entityManager.flush();
    entityManager.clear();

    // when
    Optional<Perfume> optionalPerfume =
        perfumeQueryPersistenceAdapter.findPerfumeById(perfumeJpaEntity.getId());
    Perfume resultPerfume = optionalPerfume.get();

    // then
    assertEquals(perfume.getName(), resultPerfume.getName());
    assertEquals(perfume.getStory(), resultPerfume.getStory());
    assertEquals(perfume.getConcentration(), resultPerfume.getConcentration());
    assertEquals(perfume.getPerfumeShopUrl(), resultPerfume.getPerfumeShopUrl());
    assertNotNull(resultPerfume.getCreatedAt());
    assertNotNull(resultPerfume.getUpdatedAt());
    assertNull(resultPerfume.getDeletedAt());
  }

  @Test
  void getNotePyramidIdsByPerfume() {
    // given
    Perfume perfume =
        Perfume.builder()
            .name("테싯 오 드 퍼퓸")
            .story("마음을 차분하게 가라앉혀주고 우리 몸의 감각을 일깨워주는 흙내음과 시트러스 노트의 따뜻하고 생기 넘치는 블렌드")
            .concentration(Concentration.EAU_DE_PARFUM)
            .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
            .brandId(1L)
            .categoryId(1L)
            .thumbnailId(1L)
            .build();

    PerfumeJpaEntity perfumeJpaEntity = perfumeMapper.toPerfumeJpaEntity(perfume);
    entityManager.persist(perfumeJpaEntity);

    NoteJpaEntity noteJpaEntity1 =
        NoteJpaEntity.builder().name("핑크 페퍼").description("핑크페퍼 향").thumbnailId(1L).build();
    NoteJpaEntity noteJpaEntity2 =
        NoteJpaEntity.builder().name("머스크").description("머스크 향").thumbnailId(2L).build();
    Iterable<NoteJpaEntity> noteJpaEntities =
        noteJpaRepository.saveAll(List.of(noteJpaEntity1, noteJpaEntity2));

    noteJpaEntities.forEach(
        entity ->
            entityManager.persist(
                PerfumeNoteEntity.builder()
                    .perfumeId(perfumeJpaEntity.getId())
                    .noteId(entity.getId())
                    .noteLevel(NoteLevel.TOP)
                    .build()));
    entityManager.flush();
    entityManager.clear();

    // when
    NotePyramid notePyramidIdsByPerfume =
        perfumeQueryPersistenceAdapter.getNotePyramidByPerfume(1L);
    // then
    assertNotNull(notePyramidIdsByPerfume);
  }

  @Test
  @DisplayName("Brand를 기준으로 향수 조회 시, 마지막 향수의 id가 없으면 첫 페이지를 조회한다.")
  void findPerfumesByBrand() {
    // given
    BrandEntity brandEntity = BrandEntity.builder().name("Aesop").story("기업 소개").build();
    entityManager.persist(brandEntity);

    for (int i = 0; i < 5; i++) {
      PerfumeJpaEntity perfumeJpaEntity =
          PerfumeJpaEntity.builder()
              .name("perfume" + i)
              .story("story" + i)
              .concentration(Concentration.EAU_DE_PARFUM)
              .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
              .brandId(brandEntity.getId())
              .categoryId(1L)
              .thumbnailId(1L)
              .build();

      entityManager.persist(perfumeJpaEntity);
    }
    entityManager.flush();
    entityManager.clear();

    // when
    int pageSize = 3;
    CustomSlice<SimplePerfumeResult> perfumesByBrand =
        perfumeQueryPersistenceAdapter.findPerfumesByBrand(brandEntity.getId(), null, pageSize);
    // then
    assertEquals(pageSize, perfumesByBrand.getContent().size());
    assertTrue(perfumesByBrand.isHasNext());
  }

  @Test
  @DisplayName("Brand를 기준으로 향수 조회 시, 마지막 향수의 id를 넘기면 그 이후의 향수를 조회한다.")
  void findPerfumesByBrandWithLastPerfumeId() {
    // given
    BrandEntity brandEntity = BrandEntity.builder().name("Aesop").story("기업 소개").build();
    entityManager.persist(brandEntity);

    List<PerfumeJpaEntity> perfumeJpaEntities = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      PerfumeJpaEntity perfumeJpaEntity =
          PerfumeJpaEntity.builder()
              .name("perfume" + i)
              .story("story" + i)
              .concentration(Concentration.EAU_DE_PARFUM)
              .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
              .brandId(brandEntity.getId())
              .categoryId(1L)
              .thumbnailId(1L)
              .build();

      entityManager.persist(perfumeJpaEntity);
      perfumeJpaEntities.add(perfumeJpaEntity);
    }
    entityManager.flush();
    entityManager.clear();

    // when
    int pageSize = 3;
    // 첫 3개 조회
    CustomSlice<SimplePerfumeResult> perfumesByBrandFirst =
        perfumeQueryPersistenceAdapter.findPerfumesByBrand(brandEntity.getId(), null, pageSize);
    // 위에서 구한 마지막 향수 아이디를 넣어 나머지 2개 조회
    CustomSlice<SimplePerfumeResult> perfumesByBrandLast =
        perfumeQueryPersistenceAdapter.findPerfumesByBrand(
            brandEntity.getId(),
            perfumesByBrandFirst.getContent().get(pageSize - 1).id(),
            pageSize);

    // then
    assertEquals(pageSize, perfumesByBrandFirst.getContent().size());
    assertEquals(perfumeJpaEntities.size() - pageSize, perfumesByBrandLast.getContent().size());
    assertFalse(perfumesByBrandLast.isHasNext());
  }

  @Test
  @DisplayName("Category를 기준으로 향수 목록을 조회한다.")
  void findPerfumesByCategory() {
    // given
    CategoryJpaEntity categoryJpaEntity =
        CategoryJpaEntity.builder()
            .name("플로럴")
            .description("꽃 향기를 가득 담아 사랑스러운 느낌을 줍니다.")
            .tags("#우아한 #사랑스러운 #꽃향기")
            .thumbnailId(1L)
            .createdAt(LocalDateTime.now())
            .build();

    entityManager.persist(categoryJpaEntity);

    List<PerfumeJpaEntity> perfumeJpaEntities = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      PerfumeJpaEntity perfumeJpaEntity =
          PerfumeJpaEntity.builder()
              .name("perfume" + i)
              .story("story" + i)
              .concentration(Concentration.EAU_DE_PARFUM)
              .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
              .brandId(1L)
              .categoryId(categoryJpaEntity.getId())
              .thumbnailId(1L)
              .build();

      entityManager.persist(perfumeJpaEntity);
      perfumeJpaEntities.add(perfumeJpaEntity);
    }
    entityManager.flush();
    entityManager.clear();

    // when
    int pageSize = 3;
    CustomPage<SimplePerfumeResult> perfumesByCategory =
        perfumeQueryPersistenceAdapter.findPerfumesByCategory(
            categoryJpaEntity.getId(), PageRequest.of(0, 3));

    // then
    assertEquals(pageSize, perfumesByCategory.getSize());
    assertEquals(5, perfumesByCategory.getTotalElements());
    assertTrue(perfumesByCategory.isHasNext());
  }

  @Test
  @DisplayName("향수 즐겨찾기 개수가 많은 순서대로 향수 목록을 조회한다.")
  void findPerfumesByFavorite() {
    List<PerfumeJpaEntity> perfumeJpaEntities = new ArrayList<>();
    for (int i = 1; i < 6; i++) {
      PerfumeJpaEntity perfumeJpaEntity =
          PerfumeJpaEntity.builder()
              .name("perfume" + i)
              .story("story" + i)
              .concentration(Concentration.EAU_DE_PARFUM)
              .perfumeShopUrl("https://www.perfume.com/kr/p/fragrance/" + i)
              .brandId(1L)
              .categoryId(1L)
              .thumbnailId(1L)
              .build();

      entityManager.persist(perfumeJpaEntity);
      perfumeJpaEntities.add(perfumeJpaEntity);

      for (int j = 1; j <= i; j++) {
        PerfumeFavoriteJpaEntity perfumeFavoriteJpaEntity =
            new PerfumeFavoriteJpaEntity(
                (long) j, perfumeJpaEntity.getId(), LocalDateTime.now(), LocalDateTime.now(), null);
        entityManager.persist(perfumeFavoriteJpaEntity);
      }
    }
    entityManager.flush();
    entityManager.clear();

    // when
    int pageSize = 3;
    // 첫 3개 조회
    CustomSlice<SimplePerfumeResult> perfumesByBrandFirst =
        perfumeQueryPersistenceAdapter.findPerfumesByFavorite(null, pageSize);
    // 위에서 구한 마지막 향수 아이디를 넣어 나머지 2개 조회
    CustomSlice<SimplePerfumeResult> perfumesByBrandLast =
        perfumeQueryPersistenceAdapter.findPerfumesByFavorite(
            perfumesByBrandFirst.getContent().get(pageSize - 1).id(), pageSize);

    // then
    assertEquals(pageSize, perfumesByBrandFirst.getContent().size());
    assertEquals(
        perfumesByBrandFirst.getContent().get(0).id(),
        perfumeJpaEntities.get(4).getId()); // 가장 마지막 향수가 가장 즐겨찾기가 많은 향수
    assertTrue(perfumesByBrandFirst.isHasNext());

    assertEquals(perfumeJpaEntities.size() - pageSize, perfumesByBrandLast.getContent().size());
    assertEquals(
        perfumesByBrandLast.getContent().get(1).id(),
        perfumeJpaEntities.get(0).getId()); // 가장 첫번째 향수가 가장 즐겨찾기가 적은 향수
    assertFalse(perfumesByBrandLast.isHasNext());
  }

  @Test
  @DisplayName("브랜드 이름이 검색어로 들어올 때 향수 목록을 최대 10개 조회한다.")
  void searchPerfumesByQueryWithBrand() {
    // given
    BrandEntity brandEntity = BrandEntity.builder().name("이솝").story("기업 소개").build();
    entityManager.persist(brandEntity);

    List<PerfumeJpaEntity> perfumeJpaEntities = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      PerfumeJpaEntity perfumeJpaEntity =
          PerfumeJpaEntity.builder()
              .name("perfume" + i)
              .story("story" + i)
              .concentration(Concentration.EAU_DE_PARFUM)
              .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
              .brandId(brandEntity.getId())
              .categoryId(1L)
              .thumbnailId(1L)
              .build();

      entityManager.persist(perfumeJpaEntity);
      perfumeJpaEntities.add(perfumeJpaEntity);
    }

    BrandEntity brandEntity2 = BrandEntity.builder().name("조말론").story("기업 소개").build();
    entityManager.persist(brandEntity2);

    for (int i = 10; i < 20; i++) {
      PerfumeJpaEntity perfumeJpaEntity =
          PerfumeJpaEntity.builder()
              .name("perfume" + i)
              .story("story" + i)
              .concentration(Concentration.EAU_DE_PARFUM)
              .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
              .brandId(brandEntity2.getId())
              .categoryId(1L)
              .thumbnailId(1L)
              .build();

      entityManager.persist(perfumeJpaEntity);
    }
    entityManager.flush();
    entityManager.clear();

    // when
    List<PerfumeNameResult> perfumeNameResults =
        perfumeQueryPersistenceAdapter.searchPerfumeByQuery("이솝");

    // then
    assertEquals(10, perfumeNameResults.size());
    for (int i = 0; i < perfumeNameResults.size(); i++) {
      assertEquals("이솝 perfume" + i, perfumeNameResults.get(i).perfumeNameWithBrand());
    }
  }

  @Test
  @DisplayName("향수 이름이 검색어로 들어올 때 향수 목록을 최대 10개 조회한다.")
  void searchPerfumesByQueryWithPerfume() {
    // given
    String brandName = "이솝";
    BrandEntity brandEntity = BrandEntity.builder().name(brandName).story("기업 소개").build();
    entityManager.persist(brandEntity);

    List<PerfumeJpaEntity> perfumeJpaEntities = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      PerfumeJpaEntity perfumeJpaEntity =
          PerfumeJpaEntity.builder()
              .name("perfume" + i)
              .story("story" + i)
              .concentration(Concentration.EAU_DE_PARFUM)
              .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
              .brandId(brandEntity.getId())
              .categoryId(1L)
              .thumbnailId(1L)
              .build();

      entityManager.persist(perfumeJpaEntity);
      perfumeJpaEntities.add(perfumeJpaEntity);
    }
    entityManager.flush();
    entityManager.clear();

    // when
    List<PerfumeNameResult> perfumeNameResults =
        perfumeQueryPersistenceAdapter.searchPerfumeByQuery("perfume1");

    // then
    assertEquals(10, perfumeNameResults.size());
    for (PerfumeNameResult perfumeNameResult : perfumeNameResults) {
      assertTrue(perfumeNameResult.perfumeNameWithBrand().startsWith("이솝 perfume1"));
    }
  }

  @Test
  @DisplayName("브랜드 이름과 향수 이름이 검색어로 들어올 때 향수 목록을 최대 10개 조회한다.")
  void searchPerfumesByQueryWithBrandAndPerfume() {
    // given
    String brandName = "이솝";
    BrandEntity brandEntity = BrandEntity.builder().name(brandName).story("기업 소개").build();
    entityManager.persist(brandEntity);

    List<PerfumeJpaEntity> perfumeJpaEntities = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      PerfumeJpaEntity perfumeJpaEntity =
          PerfumeJpaEntity.builder()
              .name("perfume" + i)
              .story("story" + i)
              .concentration(Concentration.EAU_DE_PARFUM)
              .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
              .brandId(brandEntity.getId())
              .categoryId(1L)
              .thumbnailId(1L)
              .build();

      entityManager.persist(perfumeJpaEntity);
      perfumeJpaEntities.add(perfumeJpaEntity);
    }
    entityManager.flush();
    entityManager.clear();

    // when
    List<PerfumeNameResult> perfumeNameResults =
        perfumeQueryPersistenceAdapter.searchPerfumeByQuery("이솝 perfume1");

    // then
    assertEquals(10, perfumeNameResults.size());
    for (PerfumeNameResult perfumeNameResult : perfumeNameResults) {
      assertTrue(perfumeNameResult.perfumeNameWithBrand().startsWith("이솝 perfume1"));
    }
  }

  @Test
  @DisplayName("브랜드 이름과 향수 이름이 검색어로 들어올 때 향수 조회에 실패하면 빈 리스트를 반환한다.")
  void failToSearchPerfumesByQueryWithBrandAndPerfume() {
    // given
    String brandName = "이솝";
    BrandEntity brandEntity = BrandEntity.builder().name(brandName).story("기업 소개").build();
    entityManager.persist(brandEntity);

    List<PerfumeJpaEntity> perfumeJpaEntities = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      PerfumeJpaEntity perfumeJpaEntity =
          PerfumeJpaEntity.builder()
              .name("perfume" + i)
              .story("story" + i)
              .concentration(Concentration.EAU_DE_PARFUM)
              .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
              .brandId(brandEntity.getId())
              .categoryId(1L)
              .thumbnailId(1L)
              .build();

      entityManager.persist(perfumeJpaEntity);
      perfumeJpaEntities.add(perfumeJpaEntity);
    }
    entityManager.flush();
    entityManager.clear();

    // when
    List<PerfumeNameResult> perfumeNameResults =
        perfumeQueryPersistenceAdapter.searchPerfumeByQuery("이솝 perfume999");

    // then
    assertEquals(0, perfumeNameResults.size());
  }
}
