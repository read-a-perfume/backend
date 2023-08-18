package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.in.dto.CreateReviewCommand;
import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import org.hibernate.validator.constraints.Length;

public record CreateReviewRequestDto(
    @Positive
    Long perfumeId,
    SEASON season,
    STRENGTH strength,
    @Positive
    Long duration,
    @NotEmpty
    String feeling,
    @NotEmpty
    String situation,
    @Length(max = 3)
    List<Long> tags
) {

  public CreateReviewCommand toCommand() {
    return new CreateReviewCommand(
        perfumeId,
        season,
        strength,
        duration,
        feeling,
        situation,
        Collections.unmodifiableList(tags)
    );
  }
}
