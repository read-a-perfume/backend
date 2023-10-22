package io.perfume.api.perfume.domain;

import lombok.Getter;

@Getter
public enum Concentration {
  EAU_FRAICHE("매우 가벼운 향", "1시간-2시간"),
  EAU_DE_COLOGNE("가벼운 향", "2시간-3시간"),
  EAU_DE_TOILETTE("적당한 향", "3시간-6시간"),
  EAU_DE_PERFUME("진한 향", "6시간-8시간"),
  PERFUME("아주 진한 향", "최대 24시간");

  private final String strength;
  private final String duration;

  private Concentration(String strength, String duration) {
    this.strength = strength;
    this.duration = duration;
  }
}
