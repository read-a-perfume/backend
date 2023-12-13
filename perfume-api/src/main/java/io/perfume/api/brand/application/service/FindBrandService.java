package io.perfume.api.brand.application.service;

import io.perfume.api.brand.application.exception.BrandNotFoundException;
import io.perfume.api.brand.application.port.in.FindBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.brand.application.port.in.dto.BrandResult;
import io.perfume.api.brand.application.port.out.BrandQueryRepository;
import io.perfume.api.brand.domain.Brand;
import io.perfume.api.file.application.exception.FileNotFoundException;
import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.domain.File;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindBrandService implements FindBrandUseCase {
  private final BrandQueryRepository brandQueryRepository;
  private final FindFileUseCase findFileUseCase;

  @Override
  public BrandResult findBrandById(Long id) {
    Brand brand =
        brandQueryRepository.findBrandById(id).orElseThrow(() -> new BrandNotFoundException(id));
    File file =
        findFileUseCase
            .findFileById(brand.getThumbnailId())
            .orElseThrow(() -> new FileNotFoundException(id));
    return BrandResult.of(brand, file);
  }

  @Override
  public BrandForPerfumeResult findBrandForPerfume(Long id) {
    Brand brand =
        brandQueryRepository.findBrandById(id).orElseThrow(() -> new BrandNotFoundException(id));
    return BrandForPerfumeResult.of(brand);
  }

  @Override
  public List<BrandResult> findAll() {
    List<Brand> brands = brandQueryRepository.findAll();
    return brands.stream()
        .map(
            brand -> {
              File file =
                  findFileUseCase
                      .findFileById(brand.getThumbnailId())
                      .orElseThrow(() -> new FileNotFoundException(brand.getThumbnailId()));
              return BrandResult.of(brand, file);
            })
        .toList();
  }
}
