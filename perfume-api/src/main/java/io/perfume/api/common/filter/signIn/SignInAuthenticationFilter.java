package io.perfume.api.common.filter.signIn;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.common.jwt.JwtFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SignInAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtFactory jwtProvider;
    private final ObjectMapper objectMapper;

    public SignInAuthenticationFilter(AuthenticationManager authenticationManager, JwtFactory jwtProvider) {
        this.objectMapper = new ObjectMapper();
        this.jwtProvider = jwtProvider;

        super.setAuthenticationManager(authenticationManager);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/v1/login", "POST"));
    }

    /**
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OIDC).
     * @return Authentication Object based on username and password
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        // request로부터 user 정보 얻기
        SignInDto signInDto;
        try {
            signInDto = objectMapper.readValue(StreamUtils.copyToString(
                    request.getInputStream(), StandardCharsets.UTF_8), SignInDto.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword());
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    /**
     * @param request
     * @param response
     * @param filter
     * @param authResult the object returned from the <tt>attemptAuthentication</tt>
     *                   method.
     *                   authenticated result comes from AuthenticationProvider.
     *                   deliver JWT to the LoginSuccessHandler!
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filter,
                                            Authentication authResult) {

        String token = jwtProvider.createAccessToken((UserDetails) authResult.getPrincipal());
        response.addHeader("Authorization", token);

        SecurityContextHolder.getContext().setAuthentication(authResult);
        try {
            response.getWriter().write(
                    objectMapper.writeValueAsString(
                            (authResult.getPrincipal())));
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String errorMessage = "Authentication failed: " + failed.getMessage();
        response.getWriter().write(
                objectMapper.writeValueAsString(errorMessage));
    }
}
