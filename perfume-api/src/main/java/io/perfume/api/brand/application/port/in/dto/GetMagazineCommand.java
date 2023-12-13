package io.perfume.api.brand.application.port.in.dto;

import dto.repository.CursorDirection;

public record GetMagazineCommand(
        Long pageSize,
        Long before,
        Long after,
        Long brandId
) {

    public Long getCursor() {
        if (before != null) return before;
        return after;
    }

    public boolean hasNext() {
        return after != null;
    }

    public boolean hasPrevious() {
        return before != null;
    }

    public CursorDirection getDirection() {
        if (before != null) return CursorDirection.PREVIOUS;
        return CursorDirection.NEXT;
    }
}
