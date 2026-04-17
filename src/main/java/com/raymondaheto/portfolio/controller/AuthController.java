package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.LoginRequest;
import com.raymondaheto.portfolio.dto.LoginResponse;
import com.raymondaheto.portfolio.security.JwtService;
import com.raymondaheto.portfolio.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AdminUserService adminUserService;
  private final JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody final LoginRequest req) {
    if (!adminUserService.authenticate(req.username(), req.password())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    final String token = jwtService.generate(req.username());
    return ResponseEntity.ok(new LoginResponse(token, jwtService.expiresAt(token)));
  }
}
