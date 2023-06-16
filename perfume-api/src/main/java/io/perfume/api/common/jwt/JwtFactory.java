package io.perfume.api.common.jwt;

import encryptor.impl.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFactory {

    private final JwtUtil jwtUtil;

    public String createAccessToken(UserDetails principal) {
        List<String> authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return jwtUtil.create(principal.getUsername(), authorities, LocalDateTime.now());
    }
}
