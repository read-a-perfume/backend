package io.perfume.api.sample.application.port.in.dto;

import java.time.LocalDateTime;

public record SampleResult(Long id, String name, LocalDateTime createdAt) {
}
