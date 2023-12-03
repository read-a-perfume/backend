package io.perfume.api.review.application.service;

import io.perfume.api.review.adapter.out.persistence.repository.ReviewQueryPersistenceAdapter;
import io.perfume.api.review.application.in.ReviewStatisticUseCase;
import io.perfume.api.review.application.in.dto.ReviewStatisticResult;
import io.perfume.api.review.domain.ReviewFeatureCount;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewStatisticService implements ReviewStatisticUseCase {

  private final ReviewQueryPersistenceAdapter reviewQueryPersistenceAdapter;

  @Override
  public ReviewStatisticResult getStatisticByPerfume(Long perfumeId) {
    ReviewFeatureCount reviewFeatureCount = reviewQueryPersistenceAdapter.getReviewFeatureCount(perfumeId);

    Long totalReviews = reviewFeatureCount.totalReviews();
    return new ReviewStatisticResult(
        calculatePercentage(reviewFeatureCount.strengthMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.durationMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.seasonMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.dayTypeMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.sexMap(), totalReviews)
    );
  }

  private static <V extends Enum<V>> Map<V, Long> calculatePercentage(Map<V, Long> map, Long totalReviews) {
    EnumSet.allOf(map.keySet().iterator().next().getDeclaringClass()).forEach(
        enumValue -> map.putIfAbsent(enumValue, 0L)
    );

    return map.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> Math.round((double) e.getValue() / totalReviews * 100)));
  }
}
