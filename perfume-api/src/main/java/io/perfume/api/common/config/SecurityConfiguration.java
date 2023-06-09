package io.perfume.api.common.config;

import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.common.auth.SignInAuthenticationFilter;
import io.perfume.api.common.jwt.JwtAuthenticationFilter;
import java.util.List;
import jwt.JsonWebTokenGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder,
                               @Qualifier("JwtAuthenticationProvider")
                               AuthenticationProvider jwtAuthenticationProvider,
                               @Qualifier("daoAuthenticationProvider")
                               AuthenticationProvider daoAuthenticationProvider
  ) {
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.authenticationManagerBuilder
        .authenticationProvider(jwtAuthenticationProvider)
        .authenticationProvider(daoAuthenticationProvider);
  }

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(
      HttpSecurity httpSecurity,
      AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository,
      AuthenticationSuccessHandler authenticationSuccessHandler,
      AuthenticationFailureHandler authenticationFailureHandler,
      OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService,
      MakeNewTokenUseCase makeNewTokenUseCase,
      JsonWebTokenGenerator jsonWebTokenGenerator,
      WhiteListConfiguration whiteListConfig) throws Exception {
    httpSecurity
        .authorizeHttpRequests(
            authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers(
                        new AntPathRequestMatcher("/docs/**"),
                        new AntPathRequestMatcher("/v1/signup/**"),
                        new AntPathRequestMatcher("/oauth2/**"),
                        new AntPathRequestMatcher("/login/oauth2/code/**"),
                        new AntPathRequestMatcher("/v1/signup/**"),
                        new AntPathRequestMatcher("/error"),
                        new AntPathRequestMatcher("/v1/access-token", HttpMethod.GET.toString())
                    ).permitAll()
                    .anyRequest().authenticated()
        )
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(CsrfConfigurer::disable)
        .cors(c -> c.configurationSource(corsConfigurationSource(whiteListConfig.getCors())))
        .formLogin(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2Login(oauth2LoginConfigurer ->
            oauth2LoginConfigurer
                .authorizationEndpoint(authorizationEndpointConfig ->
                    authorizationEndpointConfig
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(authorizationRequestRepository))
                .redirectionEndpoint(redirectionEndpointConfig ->
                    redirectionEndpointConfig
                        .baseUri("/login/oauth2/code/**"))
                .userInfoEndpoint(userInfoEndpointConfig ->
                    userInfoEndpointConfig
                        .userService(oauth2UserService)
                )
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
        )
        .exceptionHandling(exceptionHandling ->
            exceptionHandling
                .authenticationEntryPoint(
                    (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(
                        401)
                )
                .accessDeniedHandler(
                    (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(
                        403)
                ))
        .addFilterBefore(
            new SignInAuthenticationFilter(this.authenticationManagerBuilder.getOrBuild(),
                makeNewTokenUseCase), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JwtAuthenticationFilter(this.authenticationManagerBuilder.getOrBuild(),
            jsonWebTokenGenerator), UsernamePasswordAuthenticationFilter.class);

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
  public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
    return new HttpSessionOAuth2AuthorizationRequestRepository();
  }

  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return new SimpleUrlAuthenticationFailureHandler();
  }

  @Bean
  public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
    return new DefaultOAuth2UserService();
  }
}
