package io.perfume.api.common.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "whitelist")
public class WhiteListConfiguration {

  private List<String> cors;

  public List<String> getCors() {
    return cors;
  }

  public void setCors(List<String> cors) {
    this.cors = cors;
  }
}
