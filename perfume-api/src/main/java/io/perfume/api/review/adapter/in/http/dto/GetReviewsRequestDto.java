package io.perfume.api.review.adapter.in.http.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GetReviewsRequestDto {
  @Min(1)
  Integer page = 1;

  @Min(1)
  @Max(20)
  Integer size = 20;
}
