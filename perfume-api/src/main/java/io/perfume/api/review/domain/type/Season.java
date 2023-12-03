package io.perfume.api.review.domain.type;

import lombok.Getter;

@Getter
public enum Season {

  SPRING("봄"),
  SUMMER("여름"),
  FALL("가을"),
  WINTER("겨울");

  private final String description;

  Season(String description) {
    this.description = description;
  }

  public static Season of(String description) {
    for (Season season : values()) {
      if (season.getDescription().equals(description)) {
        return season;
      }
    }
    throw new IllegalArgumentException("No matching constant for [" + description + "]");
  }
}
