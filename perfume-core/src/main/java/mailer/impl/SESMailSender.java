package mailer.impl;

import mailer.MailSender;

import java.time.LocalDateTime;

public class SESMailSender implements MailSender {

    @Override
    public LocalDateTime send(String email, String title, String content) {
        return LocalDateTime.now();
    }
}
