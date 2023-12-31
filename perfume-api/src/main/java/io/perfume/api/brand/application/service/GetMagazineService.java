package io.perfume.api.brand.application.service;

import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.brand.application.port.in.GetMagazineUseCase;
import io.perfume.api.brand.application.port.in.GetTagNameUseCase;
import io.perfume.api.brand.application.port.in.dto.GetMagazineCommand;
import io.perfume.api.brand.application.port.in.dto.GetMagazineResult;
import io.perfume.api.brand.application.port.out.MagazineQueryRepository;
import io.perfume.api.brand.domain.TagName;
import io.perfume.api.file.application.exception.FileNotFoundException;
import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetMagazineService implements GetMagazineUseCase {

  private final GetTagNameUseCase getTagNameUseCase;

  private final MagazineQueryRepository magazineQueryRepository;

  private final FindFileUseCase findFileUseCase;

  private final FindUserUseCase findUserUseCase;

  public GetMagazineService(
      GetTagNameUseCase getTagNameUseCase,
      MagazineQueryRepository magazineQueryRepository,
      FindFileUseCase findFileUseCase,
      FindUserUseCase findUserUseCase) {
    this.getTagNameUseCase = getTagNameUseCase;
    this.magazineQueryRepository = magazineQueryRepository;
    this.findFileUseCase = findFileUseCase;
    this.findUserUseCase = findUserUseCase;
  }

  @Override
  public List<GetMagazineResult> getMagazines(long brandId) {
    return null;
  }

  @Override
  @Transactional
  public CursorPagination<GetMagazineResult> getMagazines(GetMagazineCommand command) {
    var pageable =
        new CursorPageable(command.pageSize(), command.getDirection(), command.getCursor());
    var magazines = magazineQueryRepository.findByBrandId(pageable, command.brandId());
    var result =
        magazines.getItems().stream()
            .map(
                item ->
                    new GetMagazineResult(
                        item.getId(),
                        item.getTitle(),
                        item.getContent(),
                        getCoverThumbnailUrl(item.getCoverThumbnailId()),
                        getProfileThumbnailUrl(item.getUserId()),
                        getTagNameUseCase.getTags(item.getId()).stream()
                            .map(TagName::getName)
                            .toList()))
            .toList();

    return CursorPagination.of(result, magazines);
  }

  private String getProfileThumbnailUrl(long userId) {
    var userResult = findUserUseCase.findUserProfileById(userId);
    return userResult.thumbnail();
  }

  private String getCoverThumbnailUrl(long thumbnailId) {
    var file =
        findFileUseCase
            .findFileById(thumbnailId)
            .orElseThrow(() -> new FileNotFoundException(thumbnailId));
    return file.getUrl();
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
