package io.perfume.api.common.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jwt.JsonWebTokenGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;


public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JsonWebTokenGenerator jsonWebTokenGenerator;

    public JwtAuthenticationFilter(JsonWebTokenGenerator jsonWebTokenGenerator) {
        this.jsonWebTokenGenerator = jsonWebTokenGenerator;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = getTokenFromHeader((HttpServletRequest) request);
        Authentication authentication = getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    @NotNull
    private Authentication getAuthentication(String jwt) {
        String email = jsonWebTokenGenerator.getSubject(jwt);
        List<? extends GrantedAuthority> roles = getRoles(jwt);
        return new UsernamePasswordAuthenticationToken(email, "", roles);
    }

    @NotNull
    private List<SimpleGrantedAuthority> getRoles(String authenticationToken) {
        return Stream.of(jsonWebTokenGenerator.getClaim(authenticationToken, "roles")).map(Object::toString).map(SimpleGrantedAuthority::new).toList();
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("JWT Token is missing");
        }

        String authenticationToken = header.substring(7).trim();
        LocalDateTime now = LocalDateTime.now();
        if (!jsonWebTokenGenerator.verify(authenticationToken, now)) {
            throw new RuntimeException("JWT Token is invalid");
        }

        return authenticationToken;
    }
}
