package io.perfume.api.review.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.review.adapter.out.persistence.repository.review.ReviewEntity;
import io.perfume.api.review.application.in.dto.ReviewStatisticResult;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import io.perfume.api.user.adapter.out.persistence.user.UserEntity;
import io.perfume.api.user.domain.Role;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@SuppressWarnings("NonAsciiCharacters")
class ReviewServiceTest {

  @Autowired private ReviewService service;

  @Autowired private EntityManager entityManager;

  @Test
  void 리뷰가_존재하지_않는_경우_빈_통계_정보를_반환한다() {
    // given

    // when
    final ReviewStatisticResult result = service.getStatisticByPerfume(1L);

    // then
    assertThat(result.strengthMap())
        .contains(
            Map.entry(Strength.HEAVY, 0L),
            Map.entry(Strength.LIGHT, 0L),
            Map.entry(Strength.MODERATE, 0L));
    assertThat(result.durationMap())
        .contains(
            Map.entry(Duration.LONG, 0L),
            Map.entry(Duration.MEDIUM, 0L),
            Map.entry(Duration.SHORT, 0L));
    assertThat(result.dayTypeMap())
        .contains(
            Map.entry(DayType.DAILY, 0L),
            Map.entry(DayType.SPECIAL, 0L),
            Map.entry(DayType.REST, 0L),
            Map.entry(DayType.TRAVEL, 0L));
    assertThat(result.seasonMap())
        .contains(
            Map.entry(Season.SPRING, 0L),
            Map.entry(Season.SUMMER, 0L),
            Map.entry(Season.FALL, 0L),
            Map.entry(Season.WINTER, 0L));
    assertThat(result.sexMap())
        .contains(Map.entry(Sex.FEMALE, 0L), Map.entry(Sex.MALE, 0L), Map.entry(Sex.OTHER, 0L));
  }

  @Test
  void 리뷰_통계_정보를_반환한다() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final long perfumeId = 1L;
    final UserEntity userEntity =
        new UserEntity(
            null,
            "test",
            "test@mail.com",
            "test",
            Role.USER,
            "test",
            LocalDate.now(),
            Sex.FEMALE,
            false,
            false,
            null,
            now,
            now,
            null);
    entityManager.persist(userEntity);
    final ReviewEntity reviewEntity =
        new ReviewEntity(
            null,
            "test",
            "test description",
            Strength.LIGHT,
            Duration.LONG,
            DayType.DAILY,
            perfumeId,
            userEntity.getId(),
            Season.SPRING,
            now,
            now,
            null);
    entityManager.persist(reviewEntity);
    entityManager.flush();
    entityManager.clear();

    // when
    final ReviewStatisticResult result = service.getStatisticByPerfume(1L);

    // then
    assertThat(result.strengthMap())
        .contains(
            Map.entry(Strength.HEAVY, 0L),
            Map.entry(Strength.LIGHT, 100L),
            Map.entry(Strength.MODERATE, 0L));
    assertThat(result.durationMap())
        .contains(
            Map.entry(Duration.LONG, 100L),
            Map.entry(Duration.MEDIUM, 0L),
            Map.entry(Duration.SHORT, 0L));
    assertThat(result.dayTypeMap())
        .contains(
            Map.entry(DayType.DAILY, 100L),
            Map.entry(DayType.SPECIAL, 0L),
            Map.entry(DayType.REST, 0L),
            Map.entry(DayType.TRAVEL, 0L));
    assertThat(result.seasonMap())
        .contains(
            Map.entry(Season.SPRING, 100L),
            Map.entry(Season.SUMMER, 0L),
            Map.entry(Season.FALL, 0L),
            Map.entry(Season.WINTER, 0L));
    assertThat(result.sexMap())
        .contains(Map.entry(Sex.FEMALE, 100L), Map.entry(Sex.MALE, 0L), Map.entry(Sex.OTHER, 0L));
  }
}
