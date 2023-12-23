package io.perfume.api.review.adapter.out.persistence.repository.review;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.common.page.CustomPage;
import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.perfume.adapter.out.persistence.perfume.PerfumeJpaEntity;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.review.application.out.review.ReviewQueryRepository;
import io.perfume.api.review.application.out.review.ReviewRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewFeatureCount;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import io.perfume.api.user.adapter.out.persistence.user.UserEntity;
import io.perfume.api.user.adapter.out.persistence.user.UserMapper;
import io.perfume.api.user.domain.Role;
import io.perfume.api.user.domain.User;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({
  ReviewQueryPersistenceAdapter.class,
  ReviewMapper.class,
  UserMapper.class,
  TestQueryDSLConfiguration.class,
  ReviewPersistenceAdapter.class
})
@DataJpaTest
@EnableJpaAuditing
class ReviewQueryPersistenceAdapterTest {

  @Autowired private ReviewQueryRepository queryRepository;

  @Autowired private ReviewRepository reviewRepository;

  @Autowired private ReviewMapper reviewMapper;

  @Autowired private UserMapper userMapper;

  @Autowired private EntityManager entityManager;

  @BeforeEach
  void setUp() {
    entityManager.flush();
    entityManager.clear();
  }

  @Test
  @DisplayName("존재하지 않는 데이터를 조회할 경우 빈 객체를 반환한다.")
  void testNotFoundReview() {
    // given

    // when
    var result = queryRepository.findById(1L);

    // then
    assertThat(result).isEmpty();
  }

  @Test
  void testFindById() {
    // given
    var now = LocalDateTime.now();
    var review =
        Review.create(
            "test",
            "test description",
            Strength.LIGHT,
            Duration.TOO_SHORT,
            DayType.DAILY,
            1L,
            1L,
            Season.SPRING,
            now);
    Review savedReview = reviewRepository.save(review);
    entityManager.flush();
    entityManager.clear();

    // when
    Optional<Review> result = queryRepository.findById(savedReview.getId());

    // then
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isPositive();
  }

  @Test
  void reviewCount() {
    // given
    PerfumeJpaEntity perfumeJpaEntity =
        PerfumeJpaEntity.builder()
            .name("perfume")
            .story("story")
            .concentration(Concentration.EAU_DE_PARFUM)
            .perfumeShopUrl("https://www.aesop.com/kr/p/fragrance/fresh/tacit-eau-de-parfum/")
            .brandId(1L)
            .categoryId(1L)
            .thumbnailId(1L)
            .build();
    entityManager.persist(perfumeJpaEntity);
    LocalDateTime now = LocalDateTime.now();

    UserEntity userEntity1 =
        UserEntity.builder()
            .username("admin")
            .email("admin@admin.com")
            .password("admin")
            .role(Role.USER)
            .sex(Sex.FEMALE)
            .marketingConsent(false)
            .promotionConsent(false)
            .build();
    entityManager.persist(userEntity1);

    UserEntity userEntity2 =
        UserEntity.builder()
            .username("admin2")
            .email("admin2@admin.com")
            .password("admin2")
            .sex(Sex.MALE)
            .role(Role.USER)
            .marketingConsent(false)
            .promotionConsent(false)
            .build();
    entityManager.persist(userEntity2);

    for (int i = 0; i < 10; i++) {
      Review review =
          Review.create(
              "좋아요",
              "그냥 제 취향입니다.",
              Strength.LIGHT,
              Duration.LONG,
              DayType.DAILY,
              1L,
              1L,
              Season.SPRING,
              now);
      entityManager.persist(reviewMapper.toEntity(review));
    }
    for (int i = 0; i < 5; i++) {
      Review review =
          Review.create(
              "좋아요",
              "그냥 제 취향입니다.",
              Strength.MODERATE,
              Duration.MEDIUM,
              DayType.TRAVEL,
              1L,
              2L,
              Season.SPRING,
              now);
      entityManager.persist(reviewMapper.toEntity(review));
    }
    for (int i = 0; i < 3; i++) {
      Review review =
          Review.create(
              "좋아요",
              "그냥 제 취향입니다.",
              Strength.HEAVY,
              Duration.SHORT,
              DayType.SPECIAL,
              1L,
              1L,
              Season.SUMMER,
              now);
      entityManager.persist(reviewMapper.toEntity(review));
    }
    entityManager.flush();
    entityManager.clear();

    // when
    ReviewFeatureCount reviewFeatureCount = queryRepository.getReviewFeatureCount(1L);

    // then
    assertThat(reviewFeatureCount.totalReviews()).isEqualTo(10 + 5 + 3);
    assertThat(reviewFeatureCount.strengthMap()).containsEntry(Strength.LIGHT, 10L);
    assertThat(reviewFeatureCount.strengthMap()).containsEntry(Strength.MODERATE, 5L);
    assertThat(reviewFeatureCount.strengthMap()).containsEntry(Strength.HEAVY, 3L);
    assertThat(reviewFeatureCount.durationMap()).containsEntry(Duration.LONG, 10L);
    assertThat(reviewFeatureCount.durationMap()).containsEntry(Duration.MEDIUM, 5L);
    assertThat(reviewFeatureCount.durationMap()).containsEntry(Duration.SHORT, 3L);
    assertThat(reviewFeatureCount.durationMap().containsKey(Duration.TOO_SHORT))
        .isFalse(); // 아예 지표가 없는 항목의 경우 저장되지 않음
  }

  @Test
  void testReviewCountByUser() {
    // given
    var now = LocalDateTime.now();
    var userId = 1L;
    var review =
        Review.create(
            "test",
            "test description",
            Strength.LIGHT,
            Duration.LONG,
            DayType.DAILY,
            1L,
            userId,
            Season.SPRING,
            now);
    var createdReview = reviewMapper.toEntity(review);
    entityManager.persist(createdReview);
    entityManager.flush();
    entityManager.clear();

    // when
    var count = queryRepository.findReviewCountByUserId(userId);

    // then
    assertThat(count).isEqualTo(1);
  }

  @Test
  @DisplayName("특정 향수에 대한 리뷰를 조회한다.")
  void testFindByPerfumeId() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final long perfumeId = 1L;
    final Review review =
        Review.create(
            "test",
            "test description",
            Strength.LIGHT,
            Duration.LONG,
            DayType.DAILY,
            perfumeId,
            1L,
            Season.SPRING,
            now);
    final ReviewEntity createdReview = reviewMapper.toEntity(review);
    entityManager.persist(createdReview);
    entityManager.flush();
    entityManager.clear();
    final Pageable pageable = Pageable.ofSize(10).withPage(0);

    // when
    final CustomPage<Review> result = queryRepository.findByPerfumeId(perfumeId, pageable);

    // then
    assertThat(result.getTotalElements()).isEqualTo(1);
    final Review actual = result.getContent().get(0);
    assertThat(actual.getId()).isEqualTo(createdReview.getId());
  }

  @Test
  @DisplayName("특정 향수에 대한 페이징 처리된 리뷰를 조회한다.")
  void testFindByPerfumeIdWithPaging() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final long perfumeId = 1L;
    IntStream.range(0, 20)
        .forEach(
            i -> {
              final Review review =
                  Review.create(
                      "test",
                      "test description",
                      Strength.LIGHT,
                      Duration.LONG,
                      DayType.DAILY,
                      perfumeId,
                      1L,
                      Season.SPRING,
                      now);
              final ReviewEntity createdReview = reviewMapper.toEntity(review);
              entityManager.persist(createdReview);
            });
    entityManager.flush();
    entityManager.clear();
    final Pageable pageable = Pageable.ofSize(10).withPage(0);

    // when
    final CustomPage<Review> result = queryRepository.findByPerfumeId(perfumeId, pageable);

    // then
    assertThat(result.getTotalElements()).isEqualTo(20);
    assertThat(result.getContent().size()).isEqualTo(10);
    assertThat(result.isHasNext()).isTrue();
  }

  @Test
  @DisplayName("특정 향수에 대한 마지막 페이지 리뷰를 조회한다.")
  void testFindByPerfumeIdWithLastPage() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final long perfumeId = 1L;
    IntStream.range(0, 15)
        .forEach(
            i -> {
              final Review review =
                  Review.create(
                      "test",
                      "test description",
                      Strength.LIGHT,
                      Duration.LONG,
                      DayType.DAILY,
                      perfumeId,
                      1L,
                      Season.SPRING,
                      now);
              final ReviewEntity createdReview = reviewMapper.toEntity(review);
              entityManager.persist(createdReview);
            });
    entityManager.flush();
    entityManager.clear();
    final Pageable pageable = Pageable.ofSize(10).withPage(1);

    // when
    final CustomPage<Review> result = queryRepository.findByPerfumeId(perfumeId, pageable);

    // then
    assertThat(result.getTotalElements()).isEqualTo(15);
    assertThat(result.getContent().size()).isEqualTo(5);
    assertThat(result.isLast()).isTrue();
  }

  @Test
  @DisplayName("향수에 대한 리뷰 통계를 조회한다.")
  void testGetStatisticByPerfume() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final long perfumeId = 1L;
    final User user = User.createSocialUser("test", "test@mail.com", "123qwe", now);
    final UserEntity userEntity = userMapper.toUserJpaEntity(user);
    entityManager.persist(userEntity);
    final Review review =
        Review.create(
            "test",
            "test description",
            Strength.LIGHT,
            Duration.LONG,
            DayType.DAILY,
            perfumeId,
            userEntity.getId(),
            Season.SPRING,
            now);
    final ReviewEntity createdReview = reviewMapper.toEntity(review);
    entityManager.persist(createdReview);
    entityManager.flush();
    entityManager.clear();

    // when
    final var result = queryRepository.getReviewFeatureCount(perfumeId);

    // then
    assertThat(result.totalReviews()).isEqualTo(1);
    assertThat(result.strengthMap()).containsEntry(Strength.LIGHT, 1L);
    assertThat(result.durationMap()).containsEntry(Duration.LONG, 1L);
    assertThat(result.dayTypeMap()).containsEntry(DayType.DAILY, 1L);
    assertThat(result.seasonMap()).containsEntry(Season.SPRING, 1L);
    assertThat(result.sexMap()).containsEntry(Sex.OTHER, 1L);
  }
}
