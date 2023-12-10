package io.perfume.api.brand.application.port.in.dto;

public record CreateBrandCommand(String name, String story, Long thumbnailId) {}
