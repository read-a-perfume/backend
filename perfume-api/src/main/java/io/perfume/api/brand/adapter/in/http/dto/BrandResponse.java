package io.perfume.api.brand.adapter.in.http.dto;

import io.perfume.api.brand.application.port.in.dto.BrandResult;
import lombok.Builder;

@Builder
public record BrandResponse(String name, String story, String thumbnailUrl) {

    public static BrandResponse of(BrandResult brandResult) {
        return BrandResponse.builder()
                .name(brandResult.name())
                .story(brandResult.story())
                .thumbnailUrl(brandResult.thumbnailUrl())
                .build();
    }
}
