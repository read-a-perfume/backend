package io.perfume.api.brand.application.service;

import io.perfume.api.brand.application.port.out.BrandQueryRepository;
import io.perfume.api.brand.application.port.in.dto.BrandResult;
import io.perfume.api.brand.domain.Brand;
import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.domain.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FindBrandServiceTest {
    @InjectMocks
    private FindBrandService findBrandService;
    @Mock
    private BrandQueryRepository brandQueryRepository;
    @Mock
    private FindFileUseCase findFileUseCase;
    @Test
    void findBrandById() throws FileNotFoundException {
        // given
        Long id = 1L;
        Long thumbnailId = 1L;
        LocalDateTime now = LocalDateTime.now();
        Brand brand = Brand.builder()
                .id(id)
                .name("CHANEL")
                .story("스토리")
                .thumbnailId(thumbnailId)
                .build();
        File file = File.builder()
                .id(id)
                .url("fileurl")
                .build();
        given(brandQueryRepository.findBrandById(id)).willReturn(Optional.ofNullable(brand));
        given(findFileUseCase.findFileById(thumbnailId)).willReturn(Optional.ofNullable(file));

        // when
        BrandResult brandResult = findBrandService.findBrandById(id);

        // then
        assertEquals(brand.getName(), brandResult.name());
        assertEquals(brand.getStory(), brandResult.story());
        assertEquals(file.getUrl(), brandResult.thumbnailUrl());
    }
}
