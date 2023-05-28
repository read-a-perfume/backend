package io.perfume.api.common.properties;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "jwt")
@Validated
public record JsonWebTokenProperties(
        @NotEmpty
        String secretKey
) {
}
