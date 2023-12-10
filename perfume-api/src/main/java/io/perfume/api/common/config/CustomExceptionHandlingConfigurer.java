package io.perfume.api.common.config;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionHandlingConfigurer
    implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {

  @Override
  public void customize(
      ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer) {
    httpSecurityExceptionHandlingConfigurer
        .authenticationEntryPoint(
            (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(401))
        .accessDeniedHandler(
            (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(403));
  }
}
