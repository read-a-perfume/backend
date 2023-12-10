package io.perfume.api.perfume.application.port.in.dto;

import io.perfume.api.perfume.domain.Concentration;
import lombok.Builder;

@Builder
public record SimplePerfumeResult(
    Long id, String name, Concentration concentration, String brandName, String thumbnail) {}
