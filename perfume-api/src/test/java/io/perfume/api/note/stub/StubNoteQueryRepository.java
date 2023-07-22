package io.perfume.api.note.stub;

import io.perfume.api.note.application.port.out.NoteQueryRepository;
import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StubNoteQueryRepository implements NoteQueryRepository {

  private final List<Note> notes = new ArrayList<>();

  @Override
  public List<Note> find() {
    return new ArrayList<>(notes);
  }

  @Override
  public Optional<Note> findById(long id) {
    return notes.stream().filter(note -> note.getId() == id).findFirst();
  }

  public void add(Note note) {
    notes.add(note);
  }

  public void clear() {
    notes.clear();
  }
}
