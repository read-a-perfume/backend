package io.perfume.api.common.configurations;

import jwt.JsonWebTokenGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JsonWebTokenGenerator jsonWebTokenGenerator;

    public JwtAuthenticationProvider(JsonWebTokenGenerator jsonWebTokenGenerator) {
        this.jsonWebTokenGenerator = jsonWebTokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            return authentication;
        }

        return getAuthentication(authentication.getCredentials().toString());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @NotNull
    private Authentication getAuthentication(String jwt) {
        String email = jsonWebTokenGenerator.getSubject(jwt);
        List<? extends GrantedAuthority> roles = getRoles(jwt);
        return new JwtAuthenticationToken(email, "", roles);
    }

    @NotNull
    private List<SimpleGrantedAuthority> getRoles(String authenticationToken) {
        return Stream.of(jsonWebTokenGenerator.getClaim(authenticationToken, "roles")).map(Object::toString).map(SimpleGrantedAuthority::new).toList();
    }
}
