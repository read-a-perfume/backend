package io.perfume.api.review.application.in.tag;

import io.perfume.api.review.application.in.dto.TagResult;
import java.util.List;

public interface GetTagUseCase {
  List<TagResult> getAll();
}
