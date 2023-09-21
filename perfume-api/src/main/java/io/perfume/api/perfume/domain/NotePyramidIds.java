package io.perfume.api.perfume.domain;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NotePyramidIds {
  private final List<Long> topNoteIds;
  private final List<Long> middleNoteIds;
  private final List<Long> baseNoteIds;

  @Builder
  public NotePyramidIds(List<Long> topNoteIds, List<Long> middleNoteIds, List<Long> baseNoteIds) {
    this.topNoteIds = Collections.unmodifiableList(topNoteIds);
    this.middleNoteIds = Collections.unmodifiableList(middleNoteIds);
    this.baseNoteIds = Collections.unmodifiableList(baseNoteIds);
  }
}
