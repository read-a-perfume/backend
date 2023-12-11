package io.perfume.api.brand.application.service;

import io.perfume.api.brand.application.port.in.AddMagazineTagUseCase;
import io.perfume.api.brand.application.port.out.MagazineTagRepository;
import io.perfume.api.brand.application.port.out.TagNameQueryRepository;
import io.perfume.api.brand.application.port.out.TagNameRepository;
import io.perfume.api.brand.domain.MagazineTag;
import io.perfume.api.brand.domain.TagName;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddMagazineTageService implements AddMagazineTagUseCase {

  private final TagNameRepository tagNameRepository;

  private final TagNameQueryRepository tagNameQueryRepository;

  private final MagazineTagRepository magazineTagRepository;

  public AddMagazineTageService(
      TagNameRepository tagNameRepository,
      TagNameQueryRepository tagNameQueryRepository,
      MagazineTagRepository magazineTagRepository) {
    this.tagNameRepository = tagNameRepository;
    this.tagNameQueryRepository = tagNameQueryRepository;
    this.magazineTagRepository = magazineTagRepository;
  }

  @Override
  @Transactional
  public List<TagName> addTags(Long magazineId, List<String> tagNames, LocalDateTime now) {
    tagNames.stream()
        .filter(name -> tagNameQueryRepository.findByName(name).isEmpty())
        .map(tagName -> TagName.create(tagName, now))
        .forEach(tagNameRepository::save);

    var tags = tagNameQueryRepository.findByNames(tagNames);
    var magazineTags =
        tags.stream().map(tag -> MagazineTag.create(magazineId, tag.getId(), now)).toList();

    magazineTagRepository.saveAll(magazineTags);
    return tags;
  }
}
