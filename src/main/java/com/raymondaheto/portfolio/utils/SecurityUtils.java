package com.raymondaheto.portfolio.utils;

import com.raymondaheto.portfolio.model.Audit;
import com.raymondaheto.portfolio.model.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class SecurityUtils {

  private SecurityUtils() {}

  public static String getCurrentUsername() {

    try {
      final Object principal =
          SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof final UserDetails userDetails) {
        return userDetails.getUsername();
      }
      return Audit.SYSTEM;
    } catch (final Exception e) {
      log.warn("error while trying to get current userName", e);
      return null;
    }
  }

  public static CustomUserDetails getCurrenUserDetails() {
    final var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.getPrincipal() instanceof final CustomUserDetails userDetails) {
      return userDetails;
    }
    throw new IllegalStateException("No authenticated user found.");
  }
}
