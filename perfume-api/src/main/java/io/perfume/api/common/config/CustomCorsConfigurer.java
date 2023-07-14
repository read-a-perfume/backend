package io.perfume.api.common.config;

import java.util.List;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Component
public class CustomCorsConfigurer implements Customizer<CorsConfigurer<HttpSecurity>> {

  private final WhiteListConfiguration whiteListConfiguration;

  public CustomCorsConfigurer(WhiteListConfiguration whiteListConfiguration) {
    this.whiteListConfiguration = whiteListConfiguration;
  }

  @Override
  public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
    httpSecurityCorsConfigurer.configurationSource(configurationSource());
  }

  private CorsConfigurationSource configurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowedOrigins(whiteListConfiguration.getCors());
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return source;
  }
}
