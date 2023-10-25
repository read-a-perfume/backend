package io.perfume.api.note.adapter.in.http;

import io.perfume.api.note.adapter.in.http.dto.CreateNoteRequestDto;
import io.perfume.api.note.adapter.in.http.dto.NoteResponse;
import io.perfume.api.note.application.port.in.CreateNoteUseCase;
import io.perfume.api.note.application.port.in.FindNoteUseCase;
import io.perfume.api.note.application.port.in.dto.CreateNoteCommand;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notes")
@RequiredArgsConstructor
@Validated
public class NoteController {
  private final FindNoteUseCase findNoteUseCase;
  private final CreateNoteUseCase createNoteUseCase;

  @GetMapping("/{id}")
  public NoteResponse findNoteById(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {
    return NoteResponse.from(findNoteUseCase.findNoteById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createNote(@RequestBody CreateNoteRequestDto createNoteRequestDto) {
    createNoteUseCase.createNote(createNoteRequestDto.toCommand());
  }
}
