package io.perfume.api.note.application.port.out;

import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.CategoryUser;

public interface NoteRepository {

  Note save(Note note);

  CategoryUser save(CategoryUser categoryUser);
}
