package io.perfume.api.review.adapter.out.persistence.repository.review;

import static com.querydsl.core.group.GroupBy.groupBy;
import static io.perfume.api.review.adapter.out.persistence.repository.review.QReviewEntity.reviewEntity;
import static io.perfume.api.user.adapter.out.persistence.user.QUserEntity.userEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.common.page.CustomPage;
import io.perfume.api.review.application.out.review.ReviewQueryRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewFeatureCount;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import java.util.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@PersistenceAdapter
public class ReviewQueryPersistenceAdapter implements ReviewQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final ReviewMapper reviewMapper;

  public ReviewQueryPersistenceAdapter(JPAQueryFactory jpaQueryFactory, ReviewMapper reviewMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.reviewMapper = reviewMapper;
  }

  @Override
  public Optional<Review> findById(Long id) {
    var entity =
        jpaQueryFactory
            .selectFrom(reviewEntity)
            .where(reviewEntity.id.eq(id).and(reviewEntity.deletedAt.isNull()))
            .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(reviewMapper.toDomain(entity));
  }

  @Override
  public List<Review> findByPage(long page, long size) {
    return jpaQueryFactory
        .selectFrom(reviewEntity)
        .where(reviewEntity.deletedAt.isNull())
        .orderBy(reviewEntity.id.desc())
        .offset(page * size)
        .limit(size)
        .fetch()
        .stream()
        .map(reviewMapper::toDomain)
        .toList();
  }

  @Override
  public ReviewFeatureCount getReviewFeatureCount(final long perfumeId) {
    final Long totalReviews =
        jpaQueryFactory
            .select(reviewEntity.id.count())
            .from(reviewEntity)
            .where(reviewEntity.perfumeId.eq(perfumeId), reviewEntity.deletedAt.isNull())
            .fetchOne();

    if (totalReviews == null || totalReviews == 0) {
      return ReviewFeatureCount.EMPTY;
    }

    Map<Strength, Long> strengthMap =
        jpaQueryFactory
            .from(reviewEntity)
            .where(reviewEntity.perfumeId.eq(perfumeId), reviewEntity.deletedAt.isNull())
            .groupBy(reviewEntity.strength)
            .transform(groupBy(reviewEntity.strength).as(reviewEntity.id.count()));

    Map<Duration, Long> durationMap =
        jpaQueryFactory
            .from(reviewEntity)
            .where(reviewEntity.perfumeId.eq(perfumeId), reviewEntity.deletedAt.isNull())
            .groupBy(reviewEntity.duration)
            .transform(groupBy(reviewEntity.duration).as(reviewEntity.id.count()));

    Map<Season, Long> seasonMap =
        jpaQueryFactory
            .from(reviewEntity)
            .where(reviewEntity.perfumeId.eq(perfumeId), reviewEntity.deletedAt.isNull())
            .groupBy(reviewEntity.season)
            .transform(groupBy(reviewEntity.season).as(reviewEntity.id.count()));

    Map<DayType, Long> dayTypeMap =
        jpaQueryFactory
            .from(reviewEntity)
            .where(reviewEntity.perfumeId.eq(perfumeId), reviewEntity.deletedAt.isNull())
            .groupBy(reviewEntity.dayType)
            .transform(groupBy(reviewEntity.dayType).as(reviewEntity.id.count()));

    Map<Sex, Long> sexMap =
        jpaQueryFactory
            .from(reviewEntity)
            .leftJoin(userEntity)
            .on(reviewEntity.userId.eq(userEntity.id), userEntity.deletedAt.isNull())
            .where(reviewEntity.perfumeId.eq(perfumeId), reviewEntity.deletedAt.isNull())
            .groupBy(userEntity.sex)
            .transform(groupBy(userEntity.sex).as(reviewEntity.id.count()));

    return new ReviewFeatureCount(
        strengthMap, durationMap, seasonMap, dayTypeMap, sexMap, totalReviews);
  }

  @Override
  public Long findReviewCountByUserId(Long userId) {
    return jpaQueryFactory
        .select(reviewEntity.count())
        .from(reviewEntity)
        .where(reviewEntity.userId.eq(userId).and(reviewEntity.deletedAt.isNull()))
        .fetchOne();
  }

  @Override
  public boolean existsReviewById(Long reviewId) {
    return jpaQueryFactory
            .from(reviewEntity)
            .where(reviewEntity.id.eq(reviewId), reviewEntity.deletedAt.isNull())
            .fetchFirst()
        != null;
  }

  @Override
  public CustomPage<Review> findByPerfumeId(final long perfumeId, final Pageable pageable) {
    final List<Review> results = fetchReviewsByPerfumeId(perfumeId, pageable);
    final long total = countReviewsByPerfumeId(perfumeId);

    return new CustomPage<>(new PageImpl<>(results, pageable, total));
  }

  private List<Review> fetchReviewsByPerfumeId(final long perfumeId, final Pageable pageable) {
    return jpaQueryFactory
        .selectFrom(reviewEntity)
        .where(reviewEntity.perfumeId.eq(perfumeId), reviewEntity.deletedAt.isNull())
        .orderBy(reviewEntity.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch()
        .stream()
        .map(reviewMapper::toDomain)
        .toList();
  }

  private long countReviewsByPerfumeId(final long perfumeId) {
    return Objects.requireNonNullElseGet(
        jpaQueryFactory
            .select(reviewEntity.count())
            .from(reviewEntity)
            .where(reviewEntity.perfumeId.eq(perfumeId), reviewEntity.deletedAt.isNull())
            .fetchOne(),
        () -> 0L);
  }
}
