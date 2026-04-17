package com.raymondaheto.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "portfolio_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String title;

  @Column(columnDefinition = "TEXT")
  private String blurb;

  @Column(name = "avatar_url")
  private String avatarUrl;

  @Column(name = "about_me", columnDefinition = "TEXT")
  private String aboutMe;

  @Column(name = "resume_url")
  private String resumeUrl;

  private String location;

  @Column(name = "contact_intro", columnDefinition = "TEXT")
  private String contactIntro;

  @Column(name = "footer_text")
  private String footerText;

  private String email;
  private String phone;
  private String linkedin;

  @Column(name = "linkedin_display")
  private String linkedinDisplay;

  private String github;

  @Column(name = "github_display")
  private String githubDisplay;

  private String facebook;

  @Column(name = "facebook_display")
  private String facebookDisplay;

  @Column(name = "x_handle")
  private String xHandle;

  @Column(name = "x_display")
  private String xDisplay;

  private String instagram;

  @Column(name = "instagram_display")
  private String instagramDisplay;
}
