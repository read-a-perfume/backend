package io.perfume.api.common.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.common.config.WhiteListConfiguration;
import io.perfume.api.common.filters.JwtAccessDeniedHandler;
import io.perfume.api.common.filters.JwtAuthenticationEntryPoint;
import io.perfume.api.common.filters.JwtAuthenticationFilter;
import io.perfume.api.common.filters.LoginAuthenticationFilter;
import io.perfume.api.common.jwt.CustomUserDetailsService;
import io.perfume.api.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtProvider jwtProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity,
            AuthorizationRequestRepository<OAuth2AuthorizationRequest> requestAuthorizationRequestRepository,
            WhiteListConfiguration whiteListConfig
    ) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests
                                        .anyRequest().permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigurationSource(whiteListConfig.getCors())))
                // form login disable
                .formLogin(AbstractHttpConfigurer::disable)
                // STATELESS로 설정하여 Session 사용　X
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // handle 401, 403 Exception
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .addFilter(loginAuthenticationFilter())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        request -> request.requestMatchers("/v1/signup/email").permitAll()
                                .anyRequest().authenticated()
                );
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(List<String> whitelistCors) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(whitelistCors);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> httpSessionOAuth2AuthorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(authenticationManager(), jwtProvider,
                new ObjectMapper());
        loginAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/v1/login", "POST"));
        return loginAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
