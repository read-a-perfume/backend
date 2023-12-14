package io.perfume.api.brand.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import io.perfume.api.brand.application.port.in.dto.BrandResult;
import io.perfume.api.brand.application.port.out.BrandQueryRepository;
import io.perfume.api.brand.domain.Brand;
import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.domain.File;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindBrandServiceTest {
  @InjectMocks private FindBrandService findBrandService;
  @Mock private BrandQueryRepository brandQueryRepository;
  @Mock private FindFileUseCase findFileUseCase;

  @Test
  void findBrandById() {
    // given
    Long id = 1L;
    Long thumbnailId = 1L;
    Brand brand =
        Brand.builder().id(id).name("CHANEL").story("스토리").thumbnailId(thumbnailId).build();
    File file = File.builder().id(id).url("fileurl").build();
    given(brandQueryRepository.findBrandById(id)).willReturn(Optional.ofNullable(brand));
    given(findFileUseCase.findFileById(thumbnailId)).willReturn(Optional.ofNullable(file));

    // when
    BrandResult brandResult = findBrandService.findBrandById(id);

    // then
    assertEquals(brand.getName(), brandResult.name());
    assertEquals(brand.getStory(), brandResult.story());
    assertEquals(file.getUrl(), brandResult.thumbnail());
  }

  @Test
  void getAll() {
    // given
    Brand brand1 = Brand.builder().id(1L).name("샤넬").story("브랜드 스토리").thumbnailId(1L).build();
    Brand brand2 = Brand.builder().id(2L).name("조말론").story("브랜드 스토리").thumbnailId(2L).build();

    File file = File.builder().id(1L).url("testurl1.com").build();

    given(brandQueryRepository.findAll()).willReturn(List.of(brand1, brand2));
    given(findFileUseCase.findFileById(anyLong())).willReturn(Optional.ofNullable(file));

    // when
    List<BrandResult> list = findBrandService.findAll();

    // then
    assertEquals(2, list.size());
    assertEquals(brand1.getName(), list.get(0).name());
    assertEquals(brand1.getStory(), list.get(0).story());
    assertEquals(file.getUrl(), list.get(0).thumbnail());

    assertEquals(brand2.getName(), list.get(1).name());
    assertEquals(brand2.getStory(), list.get(1).story());
    assertEquals(file.getUrl(), list.get(1).thumbnail());
  }
}
