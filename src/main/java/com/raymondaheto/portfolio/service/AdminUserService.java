package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.entity.AdminUser;
import com.raymondaheto.portfolio.repository.AdminUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserService {

  private final AdminUserRepository adminUserRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${app.admin.username}")
  private String adminUsername;

  @Value("${app.admin.password}")
  private String adminPassword;

  @PostConstruct
  public void seedAdminUser() {
    log.info(
        "[AdminUserService] Seeding admin — username='{}' passwordLength={}",
        adminUsername,
        adminPassword == null ? 0 : adminPassword.length());
    if (adminUserRepository.findByUsername(adminUsername).isEmpty()) {
      adminUserRepository.save(
          AdminUser.builder()
              .username(adminUsername)
              .passwordHash(passwordEncoder.encode(adminPassword))
              .build());
      log.info("[AdminUserService] Admin user created.");
    } else {
      log.info("[AdminUserService] Admin user already exists, skipping seed.");
    }
  }

  public boolean authenticate(final String username, final String rawPassword) {
    log.info("[AdminUserService] authenticate called — username='{}'", username);
    return adminUserRepository
        .findByUsername(username)
        .map(
            user -> {
              final boolean matches = passwordEncoder.matches(rawPassword, user.getPasswordHash());
              log.info("[AdminUserService] BCrypt matches={}", matches);
              return matches;
            })
        .orElseGet(
            () -> {
              log.warn("[AdminUserService] No user found with username='{}'", username);
              return false;
            });
  }
}
