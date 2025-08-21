package com.raymondaheto.portfolio.mapper;

import com.raymondaheto.portfolio.dto.CertDto;
import com.raymondaheto.portfolio.dto.CertificationsDto;
import com.raymondaheto.portfolio.dto.EducationDto;
import com.raymondaheto.portfolio.dto.ExperienceDto;
import com.raymondaheto.portfolio.dto.FooterDto;
import com.raymondaheto.portfolio.dto.LinksDto;
import com.raymondaheto.portfolio.dto.ProfileDto;
import com.raymondaheto.portfolio.dto.ProjectDto;
import com.raymondaheto.portfolio.dto.SeoDto;
import com.raymondaheto.portfolio.dto.SkillCategoryWithItemsDto;
import com.raymondaheto.portfolio.entity.*;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ProfileMapper {

  private ProfileMapper() {}

  public static ProfileDto toDto(final Profile p) {
    if (p == null) {
      return null;
    }

    final List<ProjectDto> projects =
        p.getProjects().stream()
            .map(
                pr ->
                    ProjectDto.builder()
                        .id(pr.getId())
                        .name(pr.getName())
                        .desc(pr.getDescription())
                        .tags(pr.getTags())
                        .build())
            .toList();

    final List<ExperienceDto> exps =
        p.getExperiences().stream()
            .map(
                e ->
                    ExperienceDto.builder()
                        .id(e.getId())
                        .role(e.getRole())
                        .company(e.getCompany())
                        .period(e.getPeriod())
                        .bullets(e.getBullets())
                        .stack(e.getStack())
                        .sortIndex(e.getSortIndex())
                        .build())
            .toList();

    final List<CertDto> certs =
        p.getCertifications().stream()
            .map(
                c ->
                    CertDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .badge(c.getBadgeUrl())
                        .link(c.getLink())
                        .sortIndex(c.getSortIndex())
                        .build())
            .toList();

    final List<EducationDto> edu =
        p.getEducation().stream()
            .map(
                ed ->
                    EducationDto.builder()
                        .id(ed.getId())
                        .name(ed.getName())
                        .school(ed.getSchool())
                        .years(ed.getYears())
                        .sortIndex(ed.getSortIndex())
                        .build())
            .toList();

    final List<SkillCategoryWithItemsDto> cats =
        Optional.ofNullable(p.getSkillCategories()).orElseGet(List::of).stream()
            .filter(Objects::nonNull)
            .sorted(
                Comparator.comparing(
                        (SkillCategory c) ->
                            c.getSortIndex() == null ? Integer.MAX_VALUE : c.getSortIndex())
                    .thenComparing(SkillCategory::getId, Comparator.nullsLast(Long::compareTo)))
            .map(
                c ->
                    SkillCategoryWithItemsDto.builder()
                        .id(c.getId())
                        .key(c.getKey())
                        .label(c.getLabel())
                        .sortIndex(c.getSortIndex())
                        .skills(
                            Optional.ofNullable(c.getSkills()).orElseGet(List::of).stream()
                                .filter(Objects::nonNull)
                                .sorted(
                                    Comparator.comparing(
                                            (Skill s) ->
                                                s.getSortIndex() == null
                                                    ? Integer.MAX_VALUE
                                                    : s.getSortIndex())
                                        .thenComparing(
                                            Skill::getId, Comparator.nullsLast(Long::compareTo)))
                                .map(Skill::getName)
                                .filter(Objects::nonNull)
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .distinct()
                                .toList())
                        .build())
            .toList();

    return ProfileDto.builder()
        .name(p.getName())
        .title(p.getTitle())
        .blurb(p.getBlurb())
        .avatarUrl(p.getAvatarUrl())
        .aboutMe(p.getAboutMe())
        .resumeUrl(p.getResumeUrl())
        .location(p.getLocation())
        .seo(new SeoDto(p.getSeoPageTitle(), p.getSeoDescription()))
        .contactIntro(p.getContactIntro())
        .links(toLinksDto(p.getLinks()))
        .linksDisplay(toLinksDisplayDto(p.getLinksDisplay()))
        .footer(new FooterDto(p.getFooterText()))
        .projects(projects)
        .experience(exps)
        .certifications(new CertificationsDto(certs, edu))
        .skillCategories(cats)
        .build();
  }

  private static LinksDto toLinksDto(final Links l) {
    if (l == null) {
      return null;
    }
    return LinksDto.builder()
        .email(l.getEmail())
        .phone(l.getPhone())
        .linkedin(l.getLinkedin())
        .github(l.getGithub())
        .facebook(l.getFacebook())
        .x(l.getX())
        .instagram(l.getInstagram())
        .build();
  }

  private static LinksDto toLinksDisplayDto(final LinksDisplay l) {
    if (l == null) {
      return null;
    }
    return LinksDto.builder()
        .linkedin(l.getLinkedin())
        .github(l.getGithub())
        .facebook(l.getFacebook())
        .x(l.getX())
        .instagram(l.getInstagram())
        .build();
  }
}
