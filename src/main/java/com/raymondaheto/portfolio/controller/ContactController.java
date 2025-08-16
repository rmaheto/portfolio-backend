package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.ApiResponse;
import com.raymondaheto.portfolio.exception.RecaptchaException;
import com.raymondaheto.portfolio.model.ContactRequest;
import com.raymondaheto.portfolio.service.CaptchaService;
import com.raymondaheto.portfolio.service.MailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {

  private final MailService mail;
  private final CaptchaService captcha;

  @Value("${contact.to}")
  String toEmail;

  public ContactController(final MailService mail, final CaptchaService captcha) {
    this.mail = mail;
    this.captcha = captcha;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Void>> submit(@Valid @RequestBody final ContactRequest req) {

    if (!captcha.verifyToken(req.captchaToken())) {
      throw new RecaptchaException("RECAPTCHA_INVALID");
    }

    final var body =
        """
        From: %s <%s>
        Subject: %s

        %s
        """
            .formatted(req.name(), req.email(), req.subject(), req.message());

    mail.sendContact(toEmail, req.email(), req.subject(), body);
    return ResponseEntity.accepted().build();
  }
}
