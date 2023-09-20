package io.perfume.api.perfume.application.port.in.dto;

import io.perfume.api.perfume.domain.PerfumeNote;
import java.util.List;
import java.util.stream.Collectors;

public record PerfumeNoteResult(Long id, String name, String thumbnail) {
  public static PerfumeNoteResult from(PerfumeNote perfumeNote) {
    return new PerfumeNoteResult(perfumeNote.noteId(), perfumeNote.name(), "perfumeNote.thumbnailUrl()");
  }

  public static List<PerfumeNoteResult> from(List<PerfumeNote> perfumeNotes) {
    return perfumeNotes.stream()
        .map(PerfumeNoteResult::from)
        .collect(Collectors.toList());
  }
}
