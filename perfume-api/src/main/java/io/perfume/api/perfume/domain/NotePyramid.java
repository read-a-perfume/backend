package io.perfume.api.perfume.domain;

import io.perfume.api.note.domain.Note;
import java.util.List;
import lombok.Getter;

@Getter
public class NotePyramid {
  private List<Note> topNotes;
  private List<Note> middleNotes;
  private List<Note> baseNotes;


}
