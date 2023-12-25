package mailer.impl;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import mailer.MailSender;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderImpl implements MailSender {

  private final JavaMailSender javaMailSender;
  private final String fromAccount;

  public MailSenderImpl(JavaMailSender javaMailSender, String fromEmail) {
    this.javaMailSender = javaMailSender;
    this.fromAccount = fromEmail;
  }

  @Override
  public LocalDateTime send(String email, String title, String code) {
    MimeMessage message = createMessage(email, title, code);
    try {
      javaMailSender.send(message);
    } catch (MailException e) {
      throw new RuntimeException(e);
    }
    return LocalDateTime.now();
  }

  private MimeMessage createMessage(String email, String title, String code) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();

      message.addRecipients(Message.RecipientType.TO, email);
      message.setSubject(title);
      message.setText(
          "이메일 인증 코드: "
              + code
              + "\n\n이 코드를 요청하지 않은 경우 이 이메일을 무시해도 됩니다. 다른 사용자가 실수로 이메일 주소를 입력했을 수 있습니다.");

      message.setFrom(fromAccount);
      return message;
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
