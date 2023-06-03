package io.perfume.api.common.config;

import generator.Generator;
import generator.impl.NumberGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneratorConfiguration {

    @Bean
    public Generator generator() {
        return new NumberGenerator();
    }
}
