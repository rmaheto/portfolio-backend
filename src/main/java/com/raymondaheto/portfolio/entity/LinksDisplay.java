package com.raymondaheto.portfolio.entity;

import com.raymondaheto.portfolio.interceptor.AuditInterceptor;
import com.raymondaheto.portfolio.model.Audit;
import com.raymondaheto.portfolio.model.Auditable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditInterceptor.class)
public class LinksDisplay implements Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String linkedin;
  private String github;
  private String facebook;
  private String x;
  private String instagram;
  @Embedded private Audit audit = new Audit();
}
