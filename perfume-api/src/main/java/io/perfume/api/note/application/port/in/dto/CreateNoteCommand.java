package io.perfume.api.note.application.port.in.dto;

public record CreateNoteCommand(String name, String description, Long thumbnailId) {
}
