package io.perfume.api.sample.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SampleTest {

    @Test
    void changeName() {
        // given
        Sample sample = Sample.builder().name("origin name").build();

        // when
        sample.changeName("sample name");

        // then
        assertThat(sample.getName()).isEqualTo("sample name");
    }
}
