package io.perfume.api.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

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
