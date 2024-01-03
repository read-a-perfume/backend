package io.perfume.api.review.application.in.dto;

import io.perfume.api.review.domain.Tag;

public record TagResult(Long id, String name) {
  public static TagResult from(Tag tag) {
    return new TagResult(tag.getId(), tag.getName());
  }
}
