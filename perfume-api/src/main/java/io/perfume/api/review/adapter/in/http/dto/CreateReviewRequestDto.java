package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.in.dto.CreateReviewCommand;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.hibernate.validator.constraints.Length;

public record CreateReviewRequestDto(
    Perfume perfume,
    DayType dayType,
    Strength strength,
    Season season,
    Duration duration,
    @NotEmpty String shortReview,
    String fullReview,
    @Length(max = 3) List<Long> keywords,
    List<Long> thumbnails) {

  public CreateReviewCommand toCommand(LocalDateTime now) {
    return new CreateReviewCommand(
        perfume.id,
        dayType,
        strength,
        season,
        duration,
        shortReview,
        fullReview,
        Collections.unmodifiableList(thumbnails),
        Collections.unmodifiableList(keywords),
        now);
  }

  public record Perfume(long id, String name) {}
}
