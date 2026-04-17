package com.raymondaheto.portfolio.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {

  private String name;
  private String title;
  private String blurb;
  private String avatarUrl;
  private String aboutMe;
  private String resumeUrl;
  private String location;

  private SeoDto seo;
  private String contactIntro;

  private LinksDto links;
  private LinksDto linksDisplay;

  private FooterDto footer;

  private List<ProjectDto> projects;
  private List<ExperienceDto> experience;
  private CertificationsDto certifications;
  private List<SkillCategoryWithItemsDto> skillCategories;
}
