package io.perfume.api.common.filters;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthenticationToken implements Authentication {

    private final Long id;

    public JwtAuthenticationToken(Long id) {
        this.id = id;
    }

    @Override
    public boolean isAuthenticated() {
        return this.id != null;
    }

    @Override
    public String getName() {
        return this.id.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }
}
