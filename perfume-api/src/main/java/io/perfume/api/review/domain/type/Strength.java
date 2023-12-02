package io.perfume.api.review.domain.type;

import lombok.Getter;

@Getter
public enum Strength {
  LIGHT("약함"),
  MODERATE("보통"),
  HEAVY("강함");
  private final String description;

  Strength(String description) {
    this.description = description;
  }

  public static Strength of(String description) {
    for (Strength strength : values()) {
      if (strength.getDescription().equals(description)) {
        return strength;
      }
    }
    throw new IllegalArgumentException("No matching constant for [" + description + "]");
  }
}
