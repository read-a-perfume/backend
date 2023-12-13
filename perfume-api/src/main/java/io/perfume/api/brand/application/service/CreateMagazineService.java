package io.perfume.api.brand.application.service;

import io.perfume.api.brand.application.exception.BrandNotFoundException;
import io.perfume.api.brand.application.port.in.AddMagazineTagUseCase;
import io.perfume.api.brand.application.port.in.CreateMagazineUseCase;
import io.perfume.api.brand.application.port.in.dto.CreateMagazineCommand;
import io.perfume.api.brand.application.port.in.dto.MagazineResult;
import io.perfume.api.brand.application.port.out.BrandQueryRepository;
import io.perfume.api.brand.application.port.out.MagazineRepository;
import io.perfume.api.brand.domain.Brand;
import io.perfume.api.brand.domain.Magazine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateMagazineService implements CreateMagazineUseCase {

  private final BrandQueryRepository brandRepository;

  private final MagazineRepository magazineRepository;

  private final AddMagazineTagUseCase addMagazineTagUseCase;

  public CreateMagazineService(
      BrandQueryRepository brandRepository,
      MagazineRepository magazineRepository,
      AddMagazineTagUseCase addMagazineTagUseCase) {
    this.brandRepository = brandRepository;
    this.magazineRepository = magazineRepository;
    this.addMagazineTagUseCase = addMagazineTagUseCase;
  }

  @Override
  @Transactional
  public MagazineResult create(long userId, CreateMagazineCommand command) {
    Brand brand =
        brandRepository
            .findBrandById(command.brandId())
            .orElseThrow(() -> new BrandNotFoundException(command.brandId()));

    Magazine magazine =
        magazineRepository.save(
            Magazine.create(
                command.title(),
                command.subTitle(),
                command.content(),
                command.coverThumbnailId(),
                command.thumbnailId(),
                userId,
                brand.getId(),
                command.now()));
    addMagazineTagUseCase.addTags(magazine.getId(), command.tags(), command.now());
    return MagazineResult.from(magazine);
  }
}
