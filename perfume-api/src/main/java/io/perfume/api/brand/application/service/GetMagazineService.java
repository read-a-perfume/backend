package io.perfume.api.brand.application.service;

import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.brand.application.port.in.GetMagazineUseCase;
import io.perfume.api.brand.application.port.in.GetTagNameUseCase;
import io.perfume.api.brand.application.port.in.dto.GetMagazineCommand;
import io.perfume.api.brand.application.port.in.dto.GetMagazineResult;
import io.perfume.api.brand.application.port.out.MagazineQueryRepository;
import io.perfume.api.brand.domain.TagName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMagazineService implements GetMagazineUseCase {

    private final GetTagNameUseCase getTagNameUseCase;

    private final MagazineQueryRepository magazineQueryRepository;

    public GetMagazineService(
            GetTagNameUseCase getTagNameUseCase, MagazineQueryRepository magazineQueryRepository) {
        this.getTagNameUseCase = getTagNameUseCase;
        this.magazineQueryRepository = magazineQueryRepository;
    }

    @Override
    public List<GetMagazineResult> getMagazines(long brandId) {
        return null;
    }

    @Override
    public CursorPagination<GetMagazineResult> getMagazines(GetMagazineCommand command) {
        var pageable = new CursorPageable<>(command.pageSize(), command.getDirection(), command.getCursor());
        var magazines = magazineQueryRepository.findByBrandId(pageable, command.brandId());
        var result = magazines.getItems()
                .stream().map(item -> new GetMagazineResult(
                        item.getId(),
                        item.getTitle(),
                        item.getContent(),
                        item.getCoverThumbnailId(),
                        getTagNameUseCase.getTags(item.getId())
                                .stream().map(TagName::getName).toList()
                ))
                .toList();

        return CursorPagination.of(result, magazines.hasNext(), magazines.hasPrevious());
    }

//    @Override
//    public List<GetMagazineResult> getMagazines(long brandId) {
//        return magazineQueryRepository.findByMagazines(brandId).stream()
//                .map(magazine -> new GetMagazineResult(
//                        magazine.getId(),
//                        magazine.getTitle(),
//                        magazine.getContent(),
//                        magazine.getCoverThumbnailId(),
//                        getTagNameUseCase.getTags(magazine.getId())
//                                .stream()
//                                .map(TagName::getName)
//                                .toList()
//                ))
//                .toList();
//    }
}
