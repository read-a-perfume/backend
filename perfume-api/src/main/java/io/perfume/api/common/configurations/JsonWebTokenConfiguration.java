package io.perfume.api.common.configurations;

import jwt.JsonWebTokenGenerator;
import io.perfume.api.common.properties.JsonWebTokenProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonWebTokenConfiguration {

    @Bean
    public JsonWebTokenGenerator jwtUtil(JsonWebTokenProperties jsonWebTokenProperties) {
        return new JsonWebTokenGenerator(jsonWebTokenProperties.secretKey());
    }
}
