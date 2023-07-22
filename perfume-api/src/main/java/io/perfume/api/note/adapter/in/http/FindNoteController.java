package io.perfume.api.note.adapter.in.http;

import io.perfume.api.note.adapter.in.http.dto.NoteResponse;
import io.perfume.api.note.application.port.in.FindNoteUseCase;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notes")
@Validated
public class FindNoteController {

  private final FindNoteUseCase findNoteUseCase;

  public FindNoteController(FindNoteUseCase findNoteUseCase) {
    this.findNoteUseCase = findNoteUseCase;
  }

  @GetMapping()
  public List<NoteResponse> findNotes() {
    return findNoteUseCase.findNotes().stream().map(NoteResponse::from).toList();
  }

  @GetMapping("/{id}")
  public NoteResponse findNote(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {
    return NoteResponse.from(findNoteUseCase.findNoteById(id));
  }
}
