package mailer.impl;

import java.time.LocalDateTime;
import mailer.MailSender;

public class SESMailSender implements MailSender {

  @Override
  public LocalDateTime send(String email, String title, String content) {
    return LocalDateTime.now();
  }
}
