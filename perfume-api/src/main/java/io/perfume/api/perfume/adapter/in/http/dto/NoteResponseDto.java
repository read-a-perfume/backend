package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.application.port.in.dto.PerfumeNoteResult;
import java.util.List;

public record NoteResponseDto(Long id, String name, String thumbnailUrl) {
  public static List<NoteResponseDto> of(List<PerfumeNoteResult> noteResults) {
    return noteResults.stream()
        .map(noteResult -> new NoteResponseDto(noteResult.id(), noteResult.name(), noteResult.thumbnail()))
        .toList();
  }
}
