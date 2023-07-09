package io.perfume.api.common.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthorizeHttpRequestConfigurer implements
    Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

  @Override
  public void customize(
      AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
    authorizationManagerRequestMatcherRegistry
        .requestMatchers(
            new AntPathRequestMatcher("/docs/**"),
            new AntPathRequestMatcher("/v1/signup/**"),
            new AntPathRequestMatcher("/oauth2/**"),
            new AntPathRequestMatcher("/login/oauth2/code/**"),
            new AntPathRequestMatcher("/v1/signup/**"),
            new AntPathRequestMatcher("/error"),
            new AntPathRequestMatcher("/v1/access-token", HttpMethod.GET.toString())
        ).permitAll()
        .anyRequest().authenticated();
  }
}
