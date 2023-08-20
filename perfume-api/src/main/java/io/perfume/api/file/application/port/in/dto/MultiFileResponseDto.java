package io.perfume.api.file.application.port.in.dto;

import java.util.List;

public record MultiFileResponseDto(List<SaveFileResult> saveFiles) {
}
