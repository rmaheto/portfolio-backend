package com.raymondaheto.portfolio.entity;

import com.raymondaheto.portfolio.interceptor.AuditInterceptor;
import com.raymondaheto.portfolio.model.Audit;
import com.raymondaheto.portfolio.model.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditInterceptor.class)
public class AppUser implements Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  @Column(unique = true, nullable = false)
  private String username;

  private String password;

  private boolean mustChangePassword;

  @Column(nullable = false)
  private String email;

  private String phoneNumber;

  @Column(name = "last_active_at")
  private LocalDateTime lastActiveAt;

  @Embedded private Audit audit = new Audit();
}
