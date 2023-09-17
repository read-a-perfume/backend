package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.note.application.port.in.dto.NoteResult;
import java.util.List;

public record NoteResponseDto(Long id, String name, String thumbnailUrl) {
  public static List<NoteResponseDto> of(List<NoteResult> noteResults) {
    return noteResults.stream()
        .map(noteResult -> new NoteResponseDto(noteResult.id(), noteResult.name(), noteResult.thumbnail()))
        .toList();
  }
}
