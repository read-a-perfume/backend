package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.domain.type.Strength;
import java.util.Arrays;
import java.util.List;

public record GetReviewOptionItemResponseDto(String name, String code) {

  public static List<GetReviewOptionItemResponseDto> getStrength() {
    return Arrays.stream(Strength.values())
        .map(
            strength ->
                new GetReviewOptionItemResponseDto(strength.getDescription(), strength.name()))
        .toList();
  }

  public static List<GetReviewOptionItemResponseDto> getSeason() {
    return Arrays.stream(Strength.values())
        .map(
            strength ->
                new GetReviewOptionItemResponseDto(strength.getDescription(), strength.name()))
        .toList();
  }

  public static List<GetReviewOptionItemResponseDto> getDuration() {
    return Arrays.stream(Strength.values())
        .map(
            strength ->
                new GetReviewOptionItemResponseDto(strength.getDescription(), strength.name()))
        .toList();
  }

  public static List<GetReviewOptionItemResponseDto> getDayType() {
    return Arrays.stream(Strength.values())
        .map(
            strength ->
                new GetReviewOptionItemResponseDto(strength.getDescription(), strength.name()))
        .toList();
  }
}
