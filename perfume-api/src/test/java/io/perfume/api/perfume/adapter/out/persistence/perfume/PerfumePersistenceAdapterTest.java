package io.perfume.api.perfume.adapter.out.persistence.perfume;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.perfume.domain.NotePyramidIds;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({PerfumePersistenceAdapter.class, PerfumeMapper.class})
@DataJpaTest
@EnableJpaAuditing
class PerfumePersistenceAdapterTest {
  @Autowired
  private PerfumePersistenceAdapter perfumePersistenceAdapter;
  @Test
  void save() {
    // given
    Perfume perfume = Perfume.builder()
        .name("테싯 오 드 퍼퓸")
        .story("마음을 차분하게 가라앉혀주고 우리 몸의 감각을 일깨워주는 흙내음과 시트러스 노트의 따뜻하고 생기 넘치는 블렌드")
        .concentration(Concentration.EAU_DE_PARFUM)
        .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
        .brandId(1L)
        .categoryId(1L)
        .thumbnailId(1L)
        .notePyramidIds(NotePyramidIds.builder()
            .topNoteIds(List.of(1L))
            .middleNoteIds(List.of(2L))
            .baseNoteIds(List.of(3L))
            .build())
        .build();

    // when
    Perfume savedPerfume = perfumePersistenceAdapter.save(perfume);

    // then
    assertEquals(perfume.getName(), savedPerfume.getName());
    assertEquals(perfume.getStory(), savedPerfume.getStory());
    assertEquals(perfume.getConcentration(), savedPerfume.getConcentration());
    assertEquals(perfume.getBrandId(), savedPerfume.getBrandId());
    assertEquals(perfume.getCategoryId(), savedPerfume.getCategoryId());
    assertEquals(perfume.getThumbnailId(), savedPerfume.getThumbnailId());
    assertEquals(perfume.getNotePyramidIds().getTopNoteIds(), savedPerfume.getNotePyramidIds().getTopNoteIds());
    assertEquals(perfume.getNotePyramidIds().getMiddleNoteIds(), savedPerfume.getNotePyramidIds().getMiddleNoteIds());
    assertEquals(perfume.getNotePyramidIds().getBaseNoteIds(), savedPerfume.getNotePyramidIds().getBaseNoteIds());
  }
}
