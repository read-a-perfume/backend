package io.perfume.api.note.stub;

import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;

public class StubNoteRepository implements NoteRepository {

  @Override
  public Note save(Note note) {
    return note;
  }
}
