package com.raymondaheto.portfolio.entity;

import com.raymondaheto.portfolio.interceptor.AuditInterceptor;
import com.raymondaheto.portfolio.model.Audit;
import com.raymondaheto.portfolio.model.Auditable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditInterceptor.class)
public class Profile implements Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 128)
  private String name;

  @Column(nullable = false, length = 160)
  private String title;

  @Column(length = 512)
  private String blurb;

  @Column(length = 256)
  private String avatarUrl;

  @Column(length = 4000)
  private String aboutMe;

  @Column(length = 256)
  private String resumeUrl;

  @Column(length = 128)
  private String location;

  @Column(length = 160)
  private String seoPageTitle;

  @Column(length = 512)
  private String seoDescription;

  @Column(length = 160)
  private String contactIntro;

  @Column(length = 160)
  private String footerText;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "links_id")
  private Links links;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "links_display_id")
  private LinksDisplay linksDisplay;

  @Builder.Default
  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("id ASC")
  private List<Project> projects = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("sortIndex ASC, id ASC")
  private List<Experience> experiences = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("sortIndex ASC, id ASC")
  private List<Certification> certifications = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("sortIndex ASC, id ASC")
  private List<Education> education = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("sortIndex ASC, id ASC")
  private List<SkillCategory> skillCategories = new ArrayList<>();

  @Embedded @Builder.Default private Audit audit = new Audit();
}
