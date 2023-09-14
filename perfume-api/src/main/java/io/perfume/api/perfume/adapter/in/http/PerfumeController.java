package io.perfume.api.perfume.adapter.in.http;

import io.perfume.api.perfume.adapter.in.http.dto.CreatePerfumeRequestDto;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeResponse;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.GetPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/perfumes")
@RequiredArgsConstructor
public class PerfumeController {

  private final GetPerfumeUseCase getPerfumeUseCase;
  private final CreatePerfumeUseCase createPerfumeUseCase;

  @GetMapping("/{id}")
  public PerfumeResponse findPerfumeById(@PathVariable Long id) {
        return PerfumeResponse.of(getPerfumeUseCase.getPerfume(id));
  }

  @PostMapping
  public void createPerfume(@RequestBody CreatePerfumeRequestDto createPerfumeRequestDto) {
    CreatePerfumeCommand createPerfumeCommand = new CreatePerfumeCommand(createPerfumeRequestDto.name(),
        createPerfumeRequestDto.story(),
        createPerfumeRequestDto.concentration(),
        createPerfumeRequestDto.price(),
        createPerfumeRequestDto.capacity(),
        createPerfumeRequestDto.brandId(),
        createPerfumeRequestDto.categoryId(),
        createPerfumeRequestDto.thumbnailId(),
        createPerfumeRequestDto.topNoteIds(),
        createPerfumeRequestDto.middleNoteIds(),
        createPerfumeRequestDto.baseNoteIds());
    createPerfumeUseCase.createPerfume(createPerfumeCommand);
  }
}
