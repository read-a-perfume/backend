package io.perfume.api.perfume.application.port.in.dto;

public record SimplePerfumeThemeResult(
    Long id, String name, String story, String brandName, String thumbnail) {}
