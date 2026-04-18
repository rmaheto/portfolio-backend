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

  @Value("${app.admin.email:}")
  private String adminEmail;

  @PostConstruct
  public void seedAdminUser() {
    log.info(
        "[AdminUserService] Seeding admin — username='{}' passwordLength={}",
        adminUsername,
        adminPassword == null ? 0 : adminPassword.length());

    adminUserRepository
        .findByUsername(adminUsername)
        .ifPresentOrElse(
            user -> {
              log.info("[AdminUserService] Admin user already exists.");
              if (user.getEmail() == null && adminEmail != null && !adminEmail.isBlank()) {
                user.setEmail(adminEmail);
                adminUserRepository.save(user);
                log.info("[AdminUserService] Updated admin email.");
              }
            },
            () -> {
              adminUserRepository.save(
                  AdminUser.builder()
                      .username(adminUsername)
                      .passwordHash(passwordEncoder.encode(adminPassword))
                      .email(adminEmail.isBlank() ? null : adminEmail)
                      .build());
              log.info("[AdminUserService] Admin user created.");
            });
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

  public String getAdminEmail(final String username) {
    return adminUserRepository.findByUsername(username).map(AdminUser::getEmail).orElse(null);
  }
}
