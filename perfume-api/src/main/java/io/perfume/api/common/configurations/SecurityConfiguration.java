package io.perfume.api.common.configurations;

import io.perfume.api.common.filters.JwtAuthenticationFilter;
import jwt.JsonWebTokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public SecurityConfiguration(
            AuthenticationManagerBuilder authenticationManagerBuilder,
            JwtAuthenticationProvider jwtAuthenticationProvider
    ) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity,
            AuthorizationRequestRepository<OAuth2AuthorizationRequest> requestAuthorizationRequestRepository,
            AuthenticationSuccessHandler authenticationSuccessHandler,
            AuthenticationFailureHandler authenticationFailureHandler,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService,
            JsonWebTokenGenerator jsonWebTokenGenerator,
            WhiteListConfiguration whiteListConfig
    ) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests
                                        .anyRequest().permitAll()
                )
                .csrf(CsrfConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigurationSource(whiteListConfig.getCors())))
                .oauth2Login(oAuth2LoginConfigurer ->
                        oAuth2LoginConfigurer
                                .authorizationEndpoint(authorizationEndpointConfig ->
                                        authorizationEndpointConfig
                                                .baseUri("/oauth2/authorize")
                                                .authorizationRequestRepository(requestAuthorizationRequestRepository))
                                .redirectionEndpoint(redirectionEndpointConfig ->
                                        redirectionEndpointConfig
                                                .baseUri("/login/oauth2/code/*"))
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig
                                                .userService(oAuth2UserService)
                                )
                                .successHandler(authenticationSuccessHandler)
                                .failureHandler(authenticationFailureHandler)
                )
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManagerBuilder.getOrBuild()), UsernamePasswordAuthenticationFilter.class);

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
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/");
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new DefaultOAuth2UserService();
    }
}
