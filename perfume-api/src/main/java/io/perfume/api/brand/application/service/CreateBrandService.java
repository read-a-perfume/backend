package io.perfume.api.brand.application.service;

import io.perfume.api.brand.application.port.in.CreateBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.BrandResult;
import io.perfume.api.brand.application.port.in.dto.CreateBrandCommand;
import io.perfume.api.brand.application.port.out.BrandRepository;
import io.perfume.api.brand.domain.Brand;
import io.perfume.api.file.application.exception.FileNotFoundException;
import io.perfume.api.file.application.port.out.FileQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateBrandService implements CreateBrandUseCase {

    private final BrandRepository brandRepository;

    private final FileQueryRepository fileQueryRepository;

    public CreateBrandService(BrandRepository brandRepository, FileQueryRepository fileQueryRepository) {
        this.brandRepository = brandRepository;
        this.fileQueryRepository = fileQueryRepository;
    }

    @Override
    @Transactional
    public BrandResult create(CreateBrandCommand command) {
        LocalDateTime now = LocalDateTime.now();

        // TODO : 추후 optional 수정
        Brand createdBrand = brandRepository.save(createBrand(command, now)).get();
        return BrandResult.of(createdBrand,
                fileQueryRepository.findOneByFileId(createdBrand.getThumbnailId())
                        .orElseThrow(() -> new FileNotFoundException(createdBrand.getThumbnailId())));
    }

    private Brand createBrand(CreateBrandCommand command, LocalDateTime now) {
        return Brand.builder()
                .name(command.name())
                .story(command.story())
                .thumbnailId(command.thumbnailId())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
