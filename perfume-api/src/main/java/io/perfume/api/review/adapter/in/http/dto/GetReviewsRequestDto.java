package io.perfume.api.review.adapter.in.http.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record GetReviewsRequestDto(@Min(1) long page, @Max(20) long size) {
}
