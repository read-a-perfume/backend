package io.perfume.api.brand.adapter.in.http.dto;

import io.perfume.api.brand.application.port.in.dto.GetMagazineCommand;

public record GetMagazineRequestDto(
        Long pageSize,
        Long before,
        Long after
) {

    public GetMagazineCommand toCommand(long brandId) {
        return new GetMagazineCommand(getSizeOrDefault(), before, after, brandId);
    }
    private Long getSizeOrDefault() {
        return pageSize == null ? 10 : pageSize;
    }
}

