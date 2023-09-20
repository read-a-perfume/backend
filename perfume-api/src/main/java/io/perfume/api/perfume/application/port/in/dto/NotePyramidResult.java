package io.perfume.api.perfume.application.port.in.dto;

import io.perfume.api.perfume.domain.NotePyramid;
import java.util.List;
import lombok.Builder;

@Builder
public record NotePyramidResult(List<PerfumeNoteResult> topNotes, List<PerfumeNoteResult> middleNotes, List<PerfumeNoteResult> baseNotes) {
  public static NotePyramidResult from(NotePyramid notePyramid) {
    return NotePyramidResult.builder()
        .topNotes(PerfumeNoteResult.from(notePyramid.topNotes()))
        .middleNotes(PerfumeNoteResult.from(notePyramid.middleNotes()))
        .baseNotes(PerfumeNoteResult.from(notePyramid.baseNotes()))
        .build();
  }
}
