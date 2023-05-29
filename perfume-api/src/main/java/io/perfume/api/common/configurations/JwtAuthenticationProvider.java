package io.perfume.api.common.configurations;

import jwt.JsonWebTokenGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JsonWebTokenGenerator jsonWebTokenGenerator;

    public JwtAuthenticationProvider(JsonWebTokenGenerator jsonWebTokenGenerator) {
        this.jsonWebTokenGenerator = jsonWebTokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object credentials = authentication.getCredentials();
        if (!(Objects.requireNonNull(credentials) instanceof String)) {
            return JwtAuthenticationToken.unauthorized();
        }

        String jwt = Objects.toString(credentials);
        LocalDateTime now = LocalDateTime.now();
        if (!jsonWebTokenGenerator.verify(jwt, now)) {
            return JwtAuthenticationToken.unauthorized();
        }

        return getAuthentication(jwt);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @NotNull
    private Authentication getAuthentication(String jwt) {
        String email = jsonWebTokenGenerator.getSubject(jwt);
        List<? extends GrantedAuthority> roles = getRoles(jwt);
        return JwtAuthenticationToken.authorized(email, roles);
    }

    @NotNull
    private List<SimpleGrantedAuthority> getRoles(String authenticationToken) {
        ArrayList<?> roles = jsonWebTokenGenerator.getClaim(authenticationToken, "roles", ArrayList.class);

        return roles.stream().map(String::valueOf).map(SimpleGrantedAuthority::new).toList();
    }
}
