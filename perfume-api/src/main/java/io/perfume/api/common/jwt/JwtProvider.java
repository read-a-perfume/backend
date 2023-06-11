package io.perfume.api.common.jwt;

import encryptor.impl.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * @param token
     * @return when user send access token, return Authentication object
     */
    public Authentication getAuthentication(String token) {
        Claims claims = jwtUtil.getClaims(token);
        String username = claims.get("username", String.class);
        List roles = claims.get("roles", List.class);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
    }

    public String createAccessToken(UserDetails principal) {
        List<String> authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return jwtUtil.create(principal.getUsername(), authorities, LocalDateTime.now());
    }
}
