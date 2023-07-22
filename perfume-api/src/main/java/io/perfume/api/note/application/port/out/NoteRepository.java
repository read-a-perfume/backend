package io.perfume.api.note.application.port.out;

import io.perfume.api.note.domain.Note;

public interface NoteRepository {

  Note save(Note note);
}
