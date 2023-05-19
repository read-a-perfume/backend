package io.perfume.api.configuration;

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
