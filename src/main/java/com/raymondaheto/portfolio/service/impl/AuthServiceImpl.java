package com.raymondaheto.portfolio.service.impl;

import com.raymondaheto.portfolio.model.AuthResponse;
import com.raymondaheto.portfolio.model.CustomUserDetails;
import com.raymondaheto.portfolio.model.JwtTokenData;
import com.raymondaheto.portfolio.service.AuthService;
import com.raymondaheto.portfolio.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @Override
  public AuthResponse authenticateAndGenerateToken(final String username, final String password) {

    try {
      final Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(username, password));

      final CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

      final JwtTokenData tokenData = jwtUtil.buildTokenData(userDetails);

      final String jwt = jwtUtil.generateToken(tokenData);

      return new AuthResponse(jwt, Boolean.TRUE.equals(userDetails.getMustChangePassword()));

    } catch (final AuthenticationException ex) {
      throw new IllegalArgumentException("Invalid credentials");
    }
  }
}
