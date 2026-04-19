package com.raymondaheto.portfolio.dto;

import java.util.List;
import java.util.Map;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponseDto {
  private ProfileDto profile;
  private Map<String, List<String>> skills;
  private List<ExperienceDto> experience;
  private List<ProjectDto> projects;
  private List<CertificationDto> certifications;
  private List<EducationDto> education;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProfileDto {
    private Long id;
    private String name;
    private String title;
    private String blurb;
    private String avatarUrl;
    private String aboutMe;
    private String resumeUrl;
    private String location;
    private String contactIntro;
    private String footerText;
    private String email;
    private String phone;
    private String linkedin;
    private String linkedinDisplay;
    private String github;
    private String githubDisplay;
    private String facebook;
    private String facebookDisplay;
    private String xHandle;
    private String xDisplay;
    private String instagram;
    private String instagramDisplay;
    private String activeTheme;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ExperienceDto {
    private Long id;
    private String role;
    private String company;
    private String period;
    private List<String> bullets;
    private String stack;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private List<String> tags;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CertificationDto {
    private Long id;
    private String name;
    private String badgeUrl;
    private String link;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class EducationDto {
    private Long id;
    private String name;
    private String school;
    private String years;
  }
}
