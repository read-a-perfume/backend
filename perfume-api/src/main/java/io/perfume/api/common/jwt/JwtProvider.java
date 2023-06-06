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

    public void applyHeader(HttpServletResponse response, UserDetails principal) {
        String token = create(principal);
        response.addHeader("Authorization", "Bearer " + token);
    }

    /**
     *
     * @param userDetails
     * @return when user logged in, create and send token to client server
     */
    private String create(UserDetails userDetails) {
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return jwtUtil.create(userDetails.getUsername(), authorities, LocalDateTime.now());
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
