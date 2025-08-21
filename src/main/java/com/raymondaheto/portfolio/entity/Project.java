package com.raymondaheto.portfolio.entity;

import com.raymondaheto.portfolio.interceptor.AuditInterceptor;
import com.raymondaheto.portfolio.model.Audit;
import com.raymondaheto.portfolio.model.Auditable;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditInterceptor.class)
public class Project implements Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @Column(nullable = false, length = 160)
  private String name;

  @Column(length = 1000)
  private String description;

  @ElementCollection
  @CollectionTable(name = "project_tag", joinColumns = @JoinColumn(name = "project_id"))
  @Column(name = "tag", length = 64)
  private List<String> tags = new ArrayList<>();

  @Embedded private Audit audit = new Audit();
}
