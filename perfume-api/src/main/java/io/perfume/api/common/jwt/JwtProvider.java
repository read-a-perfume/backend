package io.perfume.api.common.jwt;

import encryptor.impl.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
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
     * when user logged in, create and send access token to client server
     * @param response send to client
     * @param principal create token using it
     */
    public void sendAccessToken(HttpServletResponse response, UserDetails principal) {
        List<String> authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = jwtUtil.create(principal.getUsername(), authorities, LocalDateTime.now());
        response.addHeader("Authorization", token);
    }

    /**
     * @param token
     * @return when user send access token, return Authentication object
     */
    public Authentication getAuthentication(String token) {
        String username = jwtUtil.getUsername(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
    }
}
