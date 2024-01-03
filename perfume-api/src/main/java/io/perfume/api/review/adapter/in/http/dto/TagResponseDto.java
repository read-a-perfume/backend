package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.in.dto.TagResult;

public record TagResponseDto(Long id, String name) {
  public static TagResponseDto from(TagResult tagResult) {
    return new TagResponseDto(tagResult.id(), tagResult.name());
  }
}
