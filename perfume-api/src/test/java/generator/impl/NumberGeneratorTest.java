package generator.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberGeneratorTest {

    NumberGenerator numberGenerator = new NumberGenerator();

    @Test
    void generate() {
        // given
        int length = 10;

        // when
        String result = numberGenerator.generate(length);

        // then
        assertEquals(result.length(), length);
    }
}
