package io.perfume.api.common.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider {

    public Authentication create(String payload) {
        return new JwtAuthenticationToken(1L);
    }
}
