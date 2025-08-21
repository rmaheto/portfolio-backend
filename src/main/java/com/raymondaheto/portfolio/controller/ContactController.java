package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.ApiResponse;
import com.raymondaheto.portfolio.exception.RecaptchaException;
import com.raymondaheto.portfolio.model.ContactRequest;
import com.raymondaheto.portfolio.service.CaptchaService;
import com.raymondaheto.portfolio.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contact")
@RequiredArgsConstructor
public class ContactController {

  private final EmailService emailService;
  private final CaptchaService captcha;

  @PostMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ApiResponse<Void> submit(@Valid @RequestBody final ContactRequest req) {
    if (!captcha.verifyToken(req.captchaToken())) {
      throw new RecaptchaException("RECAPTCHA_INVALID");
    }

    emailService.sendOwnerNotification(req);
    emailService.sendAutoReply(req);

    return ApiResponse.ok(null);
  }
}
