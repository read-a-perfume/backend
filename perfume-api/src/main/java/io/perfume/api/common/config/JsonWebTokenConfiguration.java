package io.perfume.api.common.config;

import io.perfume.api.common.property.JsonWebTokenProperties;
import jwt.JsonWebTokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonWebTokenConfiguration {

    @Bean
    public JsonWebTokenGenerator jwtUtil(JsonWebTokenProperties jsonWebTokenProperties) {
        return new JsonWebTokenGenerator(jsonWebTokenProperties.secretKey());
    }
}
