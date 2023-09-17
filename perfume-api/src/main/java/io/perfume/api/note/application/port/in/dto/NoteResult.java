package io.perfume.api.note.application.port.in.dto;

import io.perfume.api.note.domain.Note;

public record NoteResult(Long id, String name, String thumbnail, String description) {

  // TODO: thumbnail 을 "" 로 설정하는 부분 개선 필요
  public static NoteResult from(Note note) {
    return new NoteResult(note.getId(), note.getName(), "", note.getDescription());
  }
}
