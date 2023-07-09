package io.perfume.api.common.config;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuth2LoginConfigurer
    implements Customizer<OAuth2LoginConfigurer<HttpSecurity>> {

  private final AuthorizationRequestRepository<OAuth2AuthorizationRequest>
      authorizationRequestRepository;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;
  private final AuthenticationFailureHandler authenticationFailureHandler;
  private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService;

  public CustomOAuth2LoginConfigurer(
      AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository,
      AuthenticationSuccessHandler authenticationSuccessHandler,
      AuthenticationFailureHandler authenticationFailureHandler,
      OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService) {
    this.authorizationRequestRepository = authorizationRequestRepository;
    this.authenticationSuccessHandler = authenticationSuccessHandler;
    this.authenticationFailureHandler = authenticationFailureHandler;
    this.oauth2UserService = oauth2UserService;
  }

  @Override
  public void customize(OAuth2LoginConfigurer<HttpSecurity> httpSecurityOAuth2LoginConfigurer) {
    httpSecurityOAuth2LoginConfigurer
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
        .failureHandler(authenticationFailureHandler);
  }
}
