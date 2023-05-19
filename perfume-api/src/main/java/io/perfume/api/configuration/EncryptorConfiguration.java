package io.perfume.api.configuration;

import encryptor.TwoWayEncryptor;
import encryptor.impl.AESEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptorConfiguration {

    @Bean
    public TwoWayEncryptor twoWayEncryptor() {
        return new AESEncryptor("0123456789abcdef");
    }
}
