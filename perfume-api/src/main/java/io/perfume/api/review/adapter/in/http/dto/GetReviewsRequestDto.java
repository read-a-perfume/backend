package io.perfume.api.review.adapter.in.http.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record GetReviewsRequestDto(
    @Min(1)
    Long page,
    @Min(1)
    @Max(20)
    Long size) {

    public Long offset() {
        return (page - 1) * size;
    }

    public Long limit() {
        return size;
    }
}
