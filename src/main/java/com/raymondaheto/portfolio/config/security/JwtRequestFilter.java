package com.raymondaheto.portfolio.config.security;

import com.raymondaheto.portfolio.repositories.AppUserRepository;
import com.raymondaheto.portfolio.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;

  private final AppUserRepository appUserRepository;

  private final JwtUtil jwtUtil;

  public static final String AUTHORIZATION = "Authorization";
  public static final String BEARER = "Bearer ";

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      @Nonnull final HttpServletResponse response,
      @Nonnull final FilterChain chain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader(AUTHORIZATION);

    if (authHeader != null && authHeader.startsWith(BEARER)) {
      final String jwt = authHeader.substring(7);
      try {
        final String username = jwtUtil.extractUsername(jwt);
        if (username != null) {
          final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

          if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
            appUserRepository
                .findByUsername(username)
                .ifPresent(
                    user -> {
                      user.setLastActiveAt(LocalDateTime.now());
                      appUserRepository.save(user);
                    });
            final UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        }
      } catch (ExpiredJwtException e) {
        log.warn("JWT expired: {}", e.getMessage());
      } catch (Exception e) {
        log.error("JWT error: {}", e.getMessage());
      }
    }

    chain.doFilter(request, response);
  }
}
