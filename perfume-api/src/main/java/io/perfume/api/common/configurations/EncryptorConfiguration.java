package io.perfume.api.common.configurations;

import encryptor.OneWayEncryptor;
import encryptor.TwoWayEncryptor;
import encryptor.impl.AESEncryptor;
import encryptor.impl.SHAEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptorConfiguration {

    @Bean
    public TwoWayEncryptor twoWayEncryptor(@Value("${encryption.aes.secret-key}") String secretKey) {
        return new AESEncryptor(secretKey);
    }

    @Bean
    public OneWayEncryptor oneWayEncryptor() {
        return new SHAEncryptor();
    }
}
