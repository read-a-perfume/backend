package io.perfume.api.brand.application.service;

import io.perfume.api.brand.application.port.in.AddMagazineTagUseCase;
import io.perfume.api.brand.application.port.in.CreateMagazineUseCase;
import io.perfume.api.brand.application.port.in.dto.CreateMagazineCommand;
import io.perfume.api.brand.application.port.in.dto.MagazineResult;
import io.perfume.api.brand.application.port.out.MagazineRepository;
import io.perfume.api.brand.domain.Magazine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateMagazineService implements CreateMagazineUseCase {

  private final MagazineRepository magazineRepository;

  private final AddMagazineTagUseCase addMagazineTagUseCase;

  public CreateMagazineService(
      MagazineRepository magazineRepository, AddMagazineTagUseCase addMagazineTagUseCase) {
    this.magazineRepository = magazineRepository;
    this.addMagazineTagUseCase = addMagazineTagUseCase;
  }

  @Override
  @Transactional
  public MagazineResult create(long userId, CreateMagazineCommand command) {
    Magazine magazine =
        magazineRepository.save(
            Magazine.create(
                command.title(),
                command.subTitle(),
                command.content(),
                command.coverThumbnailId(),
                command.thumbnailId(),
                userId,
                command.brandId(),
                command.now()));
    addMagazineTagUseCase.addTags(magazine.getId(), command.tags(), command.now());
    return MagazineResult.from(magazine);
  }
}
