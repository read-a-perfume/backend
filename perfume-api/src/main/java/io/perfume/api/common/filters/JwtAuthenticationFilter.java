package io.perfume.api.common.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    protected JwtAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider, String defaultFilterProcessesUrl){
        super(defaultFilterProcessesUrl);
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer")) {
            throw new RuntimeException("JWT is missing");
        }

        String token = header.substring(7);
        Authentication authentication = jwtAuthenticationProvider.create(token);
        return getAuthenticationManager().authenticate(authentication);
    }
}
