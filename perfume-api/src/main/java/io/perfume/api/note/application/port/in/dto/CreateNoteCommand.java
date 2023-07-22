package io.perfume.api.note.application.port.in.dto;

import io.perfume.api.note.domain.NoteCategory;

public record CreateNoteCommand(String name, NoteCategory category, Long thumbnailId) {
}
