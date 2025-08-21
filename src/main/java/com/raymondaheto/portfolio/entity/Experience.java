package com.raymondaheto.portfolio.entity;

import com.raymondaheto.portfolio.interceptor.AuditInterceptor;
import com.raymondaheto.portfolio.model.Audit;
import com.raymondaheto.portfolio.model.Auditable;
import jakarta.persistence.*;
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
public class Experience implements Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @Column(nullable = false, length = 160)
  private String role;

  @Column(nullable = false, length = 200)
  private String company;

  @Column(length = 64)
  private String period;

  @ElementCollection
  @CollectionTable(name = "experience_bullet", joinColumns = @JoinColumn(name = "experience_id"))
  @OrderColumn(name = "bullet_index")
  @Column(name = "bullet", length = 400)
  private List<String> bullets = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "experience_stack", joinColumns = @JoinColumn(name = "experience_id"))
  @OrderColumn(name = "stack_index")
  @Column(name = "name", length = 64)
  private List<String> stack = new ArrayList<>();

  @Column(name = "sort_index")
  private Integer sortIndex;

  @Embedded private Audit audit = new Audit();
}
