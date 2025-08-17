
package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.model.ContactRequest;
import jakarta.annotation.Nonnull;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;
  @Value("${contact.to}") private String ownerEmail;

  public void sendOwnerNotification(final ContactRequest req) {
    final String subject = "New contact message: " + safe(req.subject());
    final Context ctx = new Context();
    ctx.setVariable("name", req.name());
    ctx.setVariable("email", req.email());
    ctx.setVariable("subject", req.subject());
    ctx.setVariable("message", req.message());
    final String html = templateEngine.process("email-owner", ctx);

    sendHtml(ownerEmail, subject, html);
  }

  public void sendAutoReply(@Nonnull final ContactRequest req) {
    if (req.email() == null || req.email().isBlank()) return;
    final String subject = "Thanks for reaching out, " + firstName(req.name());
    final Context ctx = new Context();
    ctx.setVariable("name", req.name());
    ctx.setVariable("subject", req.subject());
    final String html = templateEngine.process("email-user", ctx);

    sendHtml(req.email(), subject, html);
  }

  private void sendHtml(@Nonnull final String to, final String subject, final String html) {
    try {
      final MimeMessage mime = mailSender.createMimeMessage();
      final MimeMessageHelper helper = new MimeMessageHelper(mime, "UTF-8");
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(html, true);
      mailSender.send(mime);
    } catch (final Exception ex) {
      log.warn("Failed to send email to: {}", to, ex);
      throw new IllegalStateException("Mail send failed", ex);
    }
  }

  private static String firstName(final String name) {
    if (name == null || name.isBlank()) return "there";
    return name.trim().split("\\s+")[0];
  }
  private static String safe(final String s) { return s == null ? "" : s; }
}
