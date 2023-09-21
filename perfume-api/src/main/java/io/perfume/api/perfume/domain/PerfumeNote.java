package io.perfume.api.perfume.domain;

import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.NoteLevel;
import lombok.Builder;

@Builder
public record PerfumeNote(Long noteId, String name, Long thumbnailId, NoteLevel noteLevel) {
}
