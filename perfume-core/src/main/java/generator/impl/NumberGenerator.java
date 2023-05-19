package generator.impl;

import generator.Generator;

import java.util.Random;

public class NumberGenerator implements Generator {

    @Override
    public String generate(int length) {
        Random random = new Random();

        StringBuilder result = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            int digit = random.nextInt(10);
            result.append(digit);
        }

        return result.toString();
    }
}
