package io.perfume.api.note.application.port.out;

import io.perfume.api.note.domain.Note;
import java.util.List;
import java.util.Optional;

public interface NoteQueryRepository {

  List<Note> find();

  Optional<Note> findById(long id);
}
