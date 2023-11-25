package io.perfume.api.perfume.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import io.perfume.api.brand.application.port.in.FindBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.NoteLevel;
import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.application.service.FindPerfumeService;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.NotePyramidIds;
import io.perfume.api.perfume.domain.Perfume;
import io.perfume.api.perfume.domain.PerfumeNote;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindPerfumeServiceTest {

  @InjectMocks
  private FindPerfumeService findPerfumeService;
  @Mock
  private PerfumeQueryRepository perfumeQueryRepository;
  @Mock
  private FindCategoryUseCase findCategoryUseCase;
  @Mock
  private FindBrandUseCase findBrandUseCase;

  @Test
  void findPerfumeById() {
    // given
    NotePyramidIds notePyramidIds = NotePyramidIds.builder()
        .topNoteIds(List.of(1L))
        .middleNoteIds(List.of(2L))
        .baseNoteIds(List.of(3L))
        .build();

    NotePyramid notePyramid = NotePyramid.builder()
        .topNotes(List.of(new PerfumeNote(1L, "핑크 페퍼", 1L, NoteLevel.TOP)))
        .middleNotes(List.of(new PerfumeNote(2L, "시트러스", 2L, NoteLevel.MIDDLE)))
        .baseNotes(List.of(new PerfumeNote(3L, "머스크", 3L, NoteLevel.BASE)))
        .build();

    Perfume perfume = Perfume.builder()
        .id(1L)
        .name("테싯 오 드 퍼퓸")
        .story("마음을 차분하게 가라앉혀주고 우리 몸의 감각을 일깨워주는 흙내음과 시트러스 노트의 따뜻하고 생기 넘치는 블렌드")
        .concentration(Concentration.EAU_DE_PARFUM)
        .price(150000L)
        .capacity(50L)
        .brandId(1L)
        .categoryId(1L)
        .thumbnailId(1L)
        .notePyramidIds(notePyramidIds)
        .build();

    given(perfumeQueryRepository.findPerfumeById(anyLong())).willReturn(Optional.ofNullable(perfume));
    CategoryResult category = new CategoryResult(1L, "시트러스", "상큼하고 톡 쏘는 향으로 가볍고 산뜻한 느낌을 줍니다.", "#상큼한 #질리지않는", "thumbnailUrl.com");
    given(findCategoryUseCase.findCategoryById(anyLong()))
        .willReturn(category);
    BrandForPerfumeResult brand = new BrandForPerfumeResult("CHANEL");
    given(findBrandUseCase.findBrandForPerfume(anyLong())).willReturn(brand);
    given(perfumeQueryRepository.getNotePyramidByPerfume(anyLong())).willReturn(notePyramid);

    // when
    PerfumeResult perfumeResult = findPerfumeService.findPerfumeById(1L);

    // then
    assertEquals(perfume.getName(), perfumeResult.name());
    assertEquals(perfume.getStory(), perfumeResult.story());
    assertEquals(perfume.getConcentration(), perfumeResult.concentration());
    assertEquals(perfume.getPrice(), perfumeResult.price());
    assertEquals(perfume.getCapacity(), perfumeResult.capacity());
    assertEquals(brand.name(), perfumeResult.brandName());
    assertEquals(category.name(), perfumeResult.categoryName());
    assertEquals(category.description(), perfumeResult.categoryDescription());
  }
}
