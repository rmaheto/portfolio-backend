package com.raymondaheto.portfolio.entity;

import com.raymondaheto.portfolio.interceptor.AuditInterceptor;
import com.raymondaheto.portfolio.model.Audit;
import com.raymondaheto.portfolio.model.Auditable;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Table(
    name = "skill_category",
    uniqueConstraints =
        @UniqueConstraint(
            name = "uk_skillcat_profile_key",
            columnNames = {"profile_id", "key"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditInterceptor.class)
public class SkillCategory implements Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false)
  private Profile profile;

  @Column(nullable = false, length = 40)
  private String key;

  @Column(nullable = false, length = 80)
  private String label;

  @Column(name = "sort_index")
  private Integer sortIndex;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("sortIndex ASC, id ASC")
  private List<Skill> skills = new ArrayList<>();

  @Embedded private Audit audit = new Audit();
}
