package mailer;

import java.time.LocalDateTime;

public interface MailSender {

    LocalDateTime send(String email, String title, String content);
}
