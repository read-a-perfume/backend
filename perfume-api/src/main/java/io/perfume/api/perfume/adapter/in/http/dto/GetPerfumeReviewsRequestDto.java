package io.perfume.api.perfume.adapter.in.http.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record GetPerfumeReviewsRequestDto(@Min(1) Integer page, @Min(1) @Max(30) Integer size) {

  @Override
  public Integer page() {
    if (page == null) {
      return 0;
    }

    return page - 1;
  }

  @Override
  public Integer size() {
    if (size == null) {
      return 10;
    }

    return size;
  }
}
