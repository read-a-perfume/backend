package io.perfume.api.sample.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SampleTest {

    @Test
    void changeName() {
        // given
        Sample sample = Sample.builder().build();

        // when
        sample.changeName("sample name");

        // then
        assertThat(sample.getName()).isEqualTo("sample name");
    }
}
