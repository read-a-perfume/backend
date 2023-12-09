package io.perfume.api.perfume.adapter.in.http;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PerfumeSort {
  BRAND,
  FAVORITE;

  @JsonCreator
  public static PerfumeSort fromString(String name) {
    for (PerfumeSort sort : PerfumeSort.values()) {
      if (sort.name().equalsIgnoreCase(name)) {
        return sort;
      }
    }
    return null;
  }

  @JsonValue
  public String getName() {
      return name().toLowerCase();
  }
}
