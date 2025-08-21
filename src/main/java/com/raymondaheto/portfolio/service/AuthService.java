package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.model.AuthResponse;

public interface AuthService {
  AuthResponse authenticateAndGenerateToken(final String username, final String password);
}
