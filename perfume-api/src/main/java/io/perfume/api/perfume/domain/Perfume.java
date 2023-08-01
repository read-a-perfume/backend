package io.perfume.api.perfume.domain;

import io.perfume.api.base.BaseTimeDomain;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Perfume extends BaseTimeDomain {

    private Long id;
    private String name;
    private String story;
    private String strength;
    private String duration;
    private Long price;
    private Long capacity;
    private Long brandId;

    @Builder
    private Perfume(Long id, String name, String story, String strength, String duration, Long price, Long capacity, Long brandId,
                    LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.name = name;
        this.story = story;
        this.strength = strength;
        this.duration = duration;
        this.price = price;
        this.capacity = capacity;
        this.brandId = brandId;
    }
}
