package io.perfume.api.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.jetbrains.annotations.NotNull;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    protected BaseEntity() {}

    protected BaseEntity(@NotNull Long id) {
        this.id = id;
    }

    @NotNull
    public Long getId() {
        return id;
    }
}
