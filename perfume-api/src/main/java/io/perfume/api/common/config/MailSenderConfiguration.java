package io.perfume.api.common.config;

import java.util.Properties;
import mailer.MailSender;
import mailer.impl.MailSenderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfiguration {
  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.port}")
  private int port;

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.properties.mail.smtp.auth}")
  private String auth;

  @Value("${spring.mail.properties.mail.smtp.timeout}")
  private String timeout;

  @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
  private String starttls;

  @Bean
  public MailSender mailSender() {
    String fromEmail = username + host.split("\\.")[1];
    return new MailSenderImpl(javaMailSender(), fromEmail);
  }

  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

    javaMailSender.setHost(host);
    javaMailSender.setPort(port);
    javaMailSender.setUsername(username);
    javaMailSender.setPassword(password);
    javaMailSender.setJavaMailProperties(getMailProperties());

    return javaMailSender;
  }

  private Properties getMailProperties() {
    Properties properties = new Properties();
    properties.setProperty("mail.smtp.auth", auth);
    properties.setProperty("mail.smtp.timeout", timeout);
    properties.setProperty("mail.smtp.starttls.enable", starttls);
    return properties;
  }
}
