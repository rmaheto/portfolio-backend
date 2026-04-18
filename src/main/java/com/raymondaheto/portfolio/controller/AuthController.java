package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.CodeSentResponse;
import com.raymondaheto.portfolio.dto.LoginRequest;
import com.raymondaheto.portfolio.dto.LoginResponse;
import com.raymondaheto.portfolio.dto.VerifyCodeRequest;
import com.raymondaheto.portfolio.security.JwtService;
import com.raymondaheto.portfolio.service.AdminUserService;
import com.raymondaheto.portfolio.service.TwoFactorService;
import com.raymondaheto.portfolio.service.impl.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AdminUserService adminUserService;
  private final JwtService jwtService;
  private final TwoFactorService twoFactorService;
  private final EmailService emailService;

  @PostMapping("/login")
  public ResponseEntity<CodeSentResponse> login(@Valid @RequestBody final LoginRequest req) {
    if (!adminUserService.authenticate(req.username(), req.password())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    final String email = adminUserService.getAdminEmail(req.username());
    if (email == null || email.isBlank()) {
      log.warn("[AuthController] Admin user '{}' has no email configured for 2FA", req.username());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    final String code = twoFactorService.generateAndStore(req.username());
    emailService.sendTwoFactorCode(email, code);
    log.info("[AuthController] 2FA code sent to '{}' for user '{}'", email, req.username());

    return ResponseEntity.ok(
        new CodeSentResponse("A verification code has been sent to your email."));
  }

  @PostMapping("/verify")
  public ResponseEntity<LoginResponse> verify(@Valid @RequestBody final VerifyCodeRequest req) {
    if (!twoFactorService.verify(req.username(), req.code())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    final String token = jwtService.generate(req.username());
    return ResponseEntity.ok(new LoginResponse(token, jwtService.expiresAt(token)));
  }
}
