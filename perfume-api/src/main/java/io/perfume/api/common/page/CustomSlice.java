package io.perfume.api.common.page;

import java.util.List;
import lombok.Getter;

@Getter
public class CustomSlice<T> {
  private final List<T> content;
  private final boolean hasNext;

  public CustomSlice(List<T> content, boolean hasNext) {
    this.content = content;
    this.hasNext = hasNext;
  }
}
