package generator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
