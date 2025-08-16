package com.raymondaheto.portfolio.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
  private final JavaMailSender mail;

  public MailService(final JavaMailSender mail) {
    this.mail = mail;
  }

  public void sendContact(
      final String to, final String from, final String subject, final String body) {
    final var msg = new SimpleMailMessage();
    msg.setTo(to);
    msg.setReplyTo(from);
    msg.setSubject("[Portfolio] " + subject);
    msg.setText(body);
    mail.send(msg);
  }
}
