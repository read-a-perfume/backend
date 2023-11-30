package io.perfume.api.review.domain.type;

import lombok.Getter;

@Getter
public enum Duration {
  TOO_SHORT("1~3시간"),
  SHORT("4~6시간"),
  MEDIUM("7~9시간"),
  LONG("9시간 이상");

  private final String description;

  Duration(String description) {
    this.description = description;
  }

  public static Duration of(String description) {
    for (Duration duration : values()) {
      if (duration.getDescription().equals(description)) {
        return duration;
      }
    }
    throw new IllegalArgumentException("No matching constant for [" + description + "]");
  }
}
