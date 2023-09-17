package io.perfume.api.note.adapter.in.http;

import io.perfume.api.note.adapter.in.http.dto.NoteResponse;
import io.perfume.api.note.application.port.in.FindNoteUseCase;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notes")
@RequiredArgsConstructor
@Validated
public class NoteController {
  private final FindNoteUseCase findNoteUseCase;

  @GetMapping("/{id}")
  public NoteResponse findNoteById(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {
    return NoteResponse.from(findNoteUseCase.findNoteById(id));
  }
}
