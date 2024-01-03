package io.perfume.api.review.application.service;

import io.perfume.api.review.adapter.out.persistence.repository.tag.TagQueryPersistenceAdapter;
import io.perfume.api.review.application.in.dto.TagResult;
import io.perfume.api.review.application.in.tag.GetTagUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTagService implements GetTagUseCase {

  private final TagQueryPersistenceAdapter tagQueryPersistenceAdapter;

  @Override
  public List<TagResult> getAll() {
    return tagQueryPersistenceAdapter.findAll().stream().map(TagResult::from).toList();
  }
}
