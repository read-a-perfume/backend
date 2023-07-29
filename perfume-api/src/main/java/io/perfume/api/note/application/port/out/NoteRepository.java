package io.perfume.api.note.application.port.out;

import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.NoteUser;

public interface NoteRepository {

  Note save(Note note);

  NoteUser save(NoteUser noteUser);
}
