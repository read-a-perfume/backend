package io.perfume.api.common.config;

import mailer.MailSender;
import mailer.impl.SESMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailSenderConfiguration {

    @Bean
    public MailSender mailSender() {
        return new SESMailSender();
    }
}
