package io.perfume.api.common.configurations;

import encryptor.impl.JwtUtil;
import io.perfume.api.common.properties.JsonWebTokenProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {
    @Bean
    public JwtUtil jwtUtil(JsonWebTokenProperties jsonWebTokenProperties) {
        return new JwtUtil();
    }
}