package io.perfume.api.common.jwt;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "jwt")
@Validated
public record JwtProperties(
        @NotEmpty
        String secretKey,
        @NotEmpty
        String redirectUri,
        @Min(60 * 60 * 2)
        int accessTokenValidityInSeconds,
        @Min(60 * 60 * 24 * 7)
        int refreshTokenValidityInSeconds
) {
}
