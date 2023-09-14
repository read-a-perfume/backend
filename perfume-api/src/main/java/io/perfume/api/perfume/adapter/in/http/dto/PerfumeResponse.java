package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import io.perfume.api.perfume.domain.Concentration;
import lombok.Builder;

@Builder
public record PerfumeResponse(String name, String story, int capacity, Long price, Concentration concentration,
                              String perfumeShopUrl, String categoryName, String categoryDescription, String brandName,
                              String thumbnailUrl) {

    public static PerfumeResponse of(PerfumeResult perfumeResult) {
        return PerfumeResponse.builder()
                .name(perfumeResult.name())
                .capacity(1)
                .price(perfumeResult.price())
                .concentration(perfumeResult.concentration())
                .perfumeShopUrl(builder().perfumeShopUrl)
                .categoryName(perfumeResult.categoryName())
                .categoryDescription(perfumeResult.categoryDescription())
                .brandName(perfumeResult.brandName())
                .thumbnailUrl(perfumeResult.thumbnailUrl())
                .build();
    }
}
