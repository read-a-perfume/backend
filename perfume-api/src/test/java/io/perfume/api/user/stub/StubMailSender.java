package io.perfume.api.user.stub;

import mailer.MailSender;

import java.time.LocalDateTime;

public class StubMailSender implements MailSender {

    private LocalDateTime sentAt;

    @Override
    public LocalDateTime send(String email, String title, String content) {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public void clear() {
        this.sentAt = null;
    }
}
