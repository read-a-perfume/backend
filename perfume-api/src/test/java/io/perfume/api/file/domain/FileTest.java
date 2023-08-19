package io.perfume.api.file.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FileTest {
    @Test
    @DisplayName("builder로 잘 생성되는지 확인한다")
    void builder() {
        // given
        Long id = 1L;
        String url = "image.url/temptemptemp";
        LocalDateTime now = LocalDateTime.now();
        // when
        File file = File.builder().id(1L).url(url).createdAt(now).updatedAt(now).build();

        // then
        assertEquals(id, file.getId());
        assertEquals(url, file.getUrl());
        assertEquals(now, file.getCreatedAt());
        assertEquals(now, file.getUpdatedAt());
        assertNull(file.getDeletedAt());
    }
}
