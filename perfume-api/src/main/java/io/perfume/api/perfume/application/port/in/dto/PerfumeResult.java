package io.perfume.api.perfume.application.port.in.dto;

import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.brand.domain.Brand;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.note.domain.Category;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.perfume.domain.Perfume;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record PerfumeResult(
        String name, String story, Long price, Concentration concentration,
        String thumbnailUrl, String categoryName, String categoryDescription, String brandName
) {

    public static PerfumeResult from(Perfume perfume, Category category, Brand brand) {
        return PerfumeResult.builder()
                .name(perfume.getName())
                .story(perfume.getStory())
                .price(perfume.getPrice())
                .concentration(perfume.getConcentration())
                .categoryName(category.getName())
                .categoryDescription(category.getDescription())
                .brandName(brand.getName())
                .build();
    }

    public static PerfumeResult from(Perfume perfume, CategoryResult categoryResult, BrandForPerfumeResult brandResult) {
        return PerfumeResult.builder()
                .name(perfume.getName())
                .story(perfume.getStory())
                .price(perfume.getPrice())
                .concentration(perfume.getConcentration())
                .categoryName(categoryResult.name())
                .categoryDescription(categoryResult.description())
                .brandName(brandResult.name())
                .build();
    }
}
