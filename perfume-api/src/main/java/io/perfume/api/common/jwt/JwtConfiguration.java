package io.perfume.api.common.jwt;

import encryptor.impl.JwtUtil;
import jwt.JsonWebTokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {
    @Bean
    public JwtUtil jwtUtil(JwtProperties jwtProperties) {
        return new JwtUtil();
    }

    @Bean
    public JsonWebTokenGenerator jsonWebTokenGenerator(JwtProperties jwtProperties) {
        return new JsonWebTokenGenerator(jwtProperties.secretKey());
    }
}
