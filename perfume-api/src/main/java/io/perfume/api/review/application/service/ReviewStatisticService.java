package io.perfume.api.review.application.service;

import io.perfume.api.review.adapter.out.persistence.repository.ReviewQueryPersistenceAdapter;
import io.perfume.api.review.application.in.ReviewStatisticUseCase;
import io.perfume.api.review.application.in.dto.ReviewStatisticResult;
import io.perfume.api.review.domain.ReviewFeatureCount;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import java.util.EnumSet;
import java.util.HashMap;
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
    if (totalReviews == 0) {
      return new ReviewStatisticResult(
          createEmptyMap(Strength.class),
          createEmptyMap(Duration.class),
          createEmptyMap(Season.class),
          createEmptyMap(DayType.class),
          createEmptyMap(Sex.class)
      );
    }
    return new ReviewStatisticResult(
        calculatePercentage(reviewFeatureCount.strengthMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.durationMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.seasonMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.dayTypeMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.sexMap(), totalReviews)
    );
  }

  private <V extends Enum<V>> Map<V, Long> calculatePercentage(Map<V, Long> map, Long totalReviews) {
    EnumSet.allOf(map.keySet().iterator().next().getDeclaringClass()).forEach(
        enumValue -> map.putIfAbsent(enumValue, 0L)
    );

    return map.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> Math.round((double) e.getValue() / totalReviews * 100)));
  }

  private <V extends Enum<V>> Map<V, Long> createEmptyMap(Class<V> enumClass) {
    Map<V, Long> map = new HashMap<>();
    EnumSet.allOf(enumClass).forEach(
        enumValue -> map.putIfAbsent(enumValue, 0L)
    );
    return map;
  }
}
