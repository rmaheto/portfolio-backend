package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.AuthRequest;
import com.raymondaheto.portfolio.model.AuthResponse;
import com.raymondaheto.portfolio.service.AuthService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public AuthResponse authenticateAndGenerateToken(
      @Nonnull @RequestBody final AuthRequest authRequest) {
    return authService.authenticateAndGenerateToken(
        authRequest.getUsername(), authRequest.getPassword());
  }
}
