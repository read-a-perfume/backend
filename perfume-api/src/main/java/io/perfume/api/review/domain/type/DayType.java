package io.perfume.api.review.domain.type;

import lombok.Getter;

@Getter
public enum DayType {
  DAILY("데일리"),
  SPECIAL("특별한 날"),
  REST("휴식 시간"),
  TRAVEL("놀러가는 날");

  private final String description;

  DayType(String description) {
    this.description = description;
  }

  public static DayType of(String description) {
    for (DayType dayType : values()) {
      if (dayType.getDescription().equals(description)) {
        return dayType;
      }
    }
    throw new IllegalArgumentException("No matching constant for [" + description + "]");
  }
}
