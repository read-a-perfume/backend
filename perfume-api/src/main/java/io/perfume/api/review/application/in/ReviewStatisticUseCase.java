package io.perfume.api.review.application.in;

import io.perfume.api.review.application.in.dto.ReviewStatisticResult;

public interface ReviewStatisticUseCase {
  ReviewStatisticResult getStatisticByPerfume(Long perfumeId);
}
