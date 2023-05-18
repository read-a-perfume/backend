package io.perfume.api.sample.adapter.in;


import io.perfume.api.sample.application.service.SampleService;
import io.perfume.api.sample.application.port.in.dto.SampleResult;
import io.perfume.api.sample.adapter.in.dto.CreateSampleRequestDto;
import io.perfume.api.sample.adapter.in.dto.SampleResponseDto;
import io.perfume.api.sample.adapter.in.dto.UpdateSampleRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<SampleResponseDto> createSample(@RequestBody @Valid CreateSampleRequestDto dto) {
        SampleResult result = sampleService.createSample(dto.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(result));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SampleResponseDto> updateSample(@PathVariable("id") @Min(1) Long id, @RequestBody @Valid UpdateSampleRequestDto dto) {
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
