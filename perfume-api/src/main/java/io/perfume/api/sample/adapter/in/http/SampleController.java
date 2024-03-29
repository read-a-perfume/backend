package io.perfume.api.sample.adapter.in.http;

import io.perfume.api.sample.adapter.in.http.dto.CreateSampleRequestDto;
import io.perfume.api.sample.adapter.in.http.dto.SampleResponseDto;
import io.perfume.api.sample.adapter.in.http.dto.UpdateSampleRequestDto;
import io.perfume.api.sample.adapter.in.http.exception.CustomSampleException;
import io.perfume.api.sample.application.port.in.dto.SampleResult;
import io.perfume.api.sample.application.service.SampleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/samples")
@RequiredArgsConstructor
public class SampleController {

  private final SampleService sampleService;

  @GetMapping
  public ResponseEntity<List<SampleResponseDto>> samples() {
    return ResponseEntity.ok(sampleService.getSamples().stream().map(this::toDto).toList());
  }

  @PostMapping
  public ResponseEntity<SampleResponseDto> createSample(
      @RequestBody @Valid CreateSampleRequestDto dto) {
    SampleResult result = sampleService.createSample(dto.name());

    return ResponseEntity.status(HttpStatus.CREATED).body(toDto(result));
  }

  @GetMapping("/exception/teapot")
  public ResponseEntity<Void> throwCustomHttpException() {
    throw new CustomSampleException();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<SampleResponseDto> updateSample(
      @PathVariable("id") @Min(1) Long id, @RequestBody @Valid UpdateSampleRequestDto dto) {
    SampleResult result = sampleService.updateSample(id, dto.name());

    return ResponseEntity.ok(toDto(result));
  }

  @GetMapping("/{id}")
  public ResponseEntity<SampleResponseDto> sample(@PathVariable("id") @Min(1) Long id) {
    SampleResult result = sampleService.getSample(id);

    return ResponseEntity.ok(toDto(result));
  }

  private SampleResponseDto toDto(SampleResult sampleResult) {
    return new SampleResponseDto(sampleResult.id(), sampleResult.name(), sampleResult.createdAt());
  }
}
