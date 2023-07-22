package io.perfume.api.note.application.port;

import io.perfume.api.note.domain.Note;

public interface NoteRepository {

  Note save(Note note);
}
