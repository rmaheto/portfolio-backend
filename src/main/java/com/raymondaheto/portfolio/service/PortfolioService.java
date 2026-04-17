package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.dto.PortfolioResponseDto;
import com.raymondaheto.portfolio.dto.PortfolioResponseDto.*;
import com.raymondaheto.portfolio.entity.*;
import com.raymondaheto.portfolio.repository.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioService {

  private final PortfolioProfileRepository profileRepo;
  private final SkillRepository skillRepo;
  private final ExperienceRepository experienceRepo;
  private final ProjectRepository projectRepo;
  private final CertificationRepository certRepo;
  private final EducationRepository educationRepo;

  @Transactional(readOnly = true)
  public PortfolioResponseDto getPortfolio() {
    PortfolioProfile profile = profileRepo.findAll().stream().findFirst().orElse(null);

    Map<String, List<String>> skills = new LinkedHashMap<>();
    skillRepo.findAllByOrderByCategoryAscDisplayOrderAsc().forEach(s ->
        skills.computeIfAbsent(s.getCategory(), k -> new ArrayList<>()).add(s.getName()));

    List<ExperienceDto> experience = experienceRepo.findAllByOrderByDisplayOrderAsc().stream()
        .map(e -> ExperienceDto.builder()
            .id(e.getId())
            .role(e.getRole())
            .company(e.getCompany())
            .period(e.getPeriod())
            .stack(e.getStack())
            .bullets(e.getBullets().stream().map(ExperienceBullet::getText).toList())
            .build())
        .toList();

    List<ProjectDto> projects = projectRepo.findAllByOrderByDisplayOrderAsc().stream()
        .map(p -> ProjectDto.builder()
            .id(p.getId())
            .name(p.getName())
            .description(p.getDescription())
            .tags(p.getTags().stream().map(ProjectTag::getTag).toList())
            .build())
        .toList();

    List<CertificationDto> certs = certRepo.findAllByOrderByDisplayOrderAsc().stream()
        .map(c -> CertificationDto.builder()
            .id(c.getId())
            .name(c.getName())
            .badgeUrl(c.getBadgeUrl())
            .link(c.getLink())
            .build())
        .toList();

    List<EducationDto> education = educationRepo.findAllByOrderByDisplayOrderAsc().stream()
        .map(edu -> EducationDto.builder()
            .id(edu.getId())
            .name(edu.getName())
            .school(edu.getSchool())
            .years(edu.getYears())
            .build())
        .toList();

    return PortfolioResponseDto.builder()
        .profile(profile == null ? null : toProfileDto(profile))
        .skills(skills)
        .experience(experience)
        .projects(projects)
        .certifications(certs)
        .education(education)
        .build();
  }

  // ── Profile ───────────────────────────────────────────────────────────────

  @Transactional
  public ProfileDto updateProfile(ProfileDto dto) {
    PortfolioProfile profile = profileRepo.findAll().stream().findFirst()
        .orElse(new PortfolioProfile());
    applyProfile(profile, dto);
    return toProfileDto(profileRepo.save(profile));
  }

  // ── Skills ────────────────────────────────────────────────────────────────

  public List<Skill> getSkills() {
    return skillRepo.findAllByOrderByCategoryAscDisplayOrderAsc();
  }

  @Transactional
  public Skill addSkill(Skill skill) {
    return skillRepo.save(skill);
  }

  @Transactional
  public Skill updateSkill(Long id, Skill updated) {
    Skill skill = skillRepo.findById(id).orElseThrow();
    skill.setCategory(updated.getCategory());
    skill.setName(updated.getName());
    skill.setDisplayOrder(updated.getDisplayOrder());
    return skillRepo.save(skill);
  }

  @Transactional
  public void deleteSkill(Long id) {
    skillRepo.deleteById(id);
  }

  // ── Experience ────────────────────────────────────────────────────────────

  public List<Experience> getExperiences() {
    return experienceRepo.findAllByOrderByDisplayOrderAsc();
  }

  @Transactional
  public Experience addExperience(Experience exp) {
    exp.getBullets().forEach(b -> b.setExperience(exp));
    return experienceRepo.save(exp);
  }

  @Transactional
  public Experience updateExperience(Long id, Experience updated) {
    Experience exp = experienceRepo.findById(id).orElseThrow();
    exp.setRole(updated.getRole());
    exp.setCompany(updated.getCompany());
    exp.setPeriod(updated.getPeriod());
    exp.setStack(updated.getStack());
    exp.setDisplayOrder(updated.getDisplayOrder());
    exp.getBullets().clear();
    updated.getBullets().forEach(b -> {
      b.setExperience(exp);
      exp.getBullets().add(b);
    });
    return experienceRepo.save(exp);
  }

  @Transactional
  public void deleteExperience(Long id) {
    experienceRepo.deleteById(id);
  }

  // ── Projects ──────────────────────────────────────────────────────────────

  public List<Project> getProjects() {
    return projectRepo.findAllByOrderByDisplayOrderAsc();
  }

  @Transactional
  public Project addProject(Project project) {
    project.getTags().forEach(t -> t.setProject(project));
    return projectRepo.save(project);
  }

  @Transactional
  public Project updateProject(Long id, Project updated) {
    Project project = projectRepo.findById(id).orElseThrow();
    project.setName(updated.getName());
    project.setDescription(updated.getDescription());
    project.setDisplayOrder(updated.getDisplayOrder());
    project.getTags().clear();
    updated.getTags().forEach(t -> {
      t.setProject(project);
      project.getTags().add(t);
    });
    return projectRepo.save(project);
  }

  @Transactional
  public void deleteProject(Long id) {
    projectRepo.deleteById(id);
  }

  // ── Certifications ────────────────────────────────────────────────────────

  public List<Certification> getCertifications() {
    return certRepo.findAllByOrderByDisplayOrderAsc();
  }

  @Transactional
  public Certification addCertification(Certification cert) {
    return certRepo.save(cert);
  }

  @Transactional
  public Certification updateCertification(Long id, Certification updated) {
    Certification cert = certRepo.findById(id).orElseThrow();
    cert.setName(updated.getName());
    cert.setBadgeUrl(updated.getBadgeUrl());
    cert.setLink(updated.getLink());
    cert.setDisplayOrder(updated.getDisplayOrder());
    return certRepo.save(cert);
  }

  @Transactional
  public void deleteCertification(Long id) {
    certRepo.deleteById(id);
  }

  // ── Education ─────────────────────────────────────────────────────────────

  public List<Education> getEducation() {
    return educationRepo.findAllByOrderByDisplayOrderAsc();
  }

  @Transactional
  public Education addEducation(Education edu) {
    return educationRepo.save(edu);
  }

  @Transactional
  public Education updateEducation(Long id, Education updated) {
    Education edu = educationRepo.findById(id).orElseThrow();
    edu.setName(updated.getName());
    edu.setSchool(updated.getSchool());
    edu.setYears(updated.getYears());
    edu.setDisplayOrder(updated.getDisplayOrder());
    return educationRepo.save(edu);
  }

  @Transactional
  public void deleteEducation(Long id) {
    educationRepo.deleteById(id);
  }

  // ── Helpers ───────────────────────────────────────────────────────────────

  private void applyProfile(PortfolioProfile p, ProfileDto dto) {
    p.setName(dto.getName());
    p.setTitle(dto.getTitle());
    p.setBlurb(dto.getBlurb());
    p.setAvatarUrl(dto.getAvatarUrl());
    p.setAboutMe(dto.getAboutMe());
    p.setResumeUrl(dto.getResumeUrl());
    p.setLocation(dto.getLocation());
    p.setContactIntro(dto.getContactIntro());
    p.setFooterText(dto.getFooterText());
    p.setEmail(dto.getEmail());
    p.setPhone(dto.getPhone());
    p.setLinkedin(dto.getLinkedin());
    p.setLinkedinDisplay(dto.getLinkedinDisplay());
    p.setGithub(dto.getGithub());
    p.setGithubDisplay(dto.getGithubDisplay());
    p.setFacebook(dto.getFacebook());
    p.setFacebookDisplay(dto.getFacebookDisplay());
    p.setXHandle(dto.getXHandle());
    p.setXDisplay(dto.getXDisplay());
    p.setInstagram(dto.getInstagram());
    p.setInstagramDisplay(dto.getInstagramDisplay());
  }

  private ProfileDto toProfileDto(PortfolioProfile p) {
    return ProfileDto.builder()
        .id(p.getId())
        .name(p.getName())
        .title(p.getTitle())
        .blurb(p.getBlurb())
        .avatarUrl(p.getAvatarUrl())
        .aboutMe(p.getAboutMe())
        .resumeUrl(p.getResumeUrl())
        .location(p.getLocation())
        .contactIntro(p.getContactIntro())
        .footerText(p.getFooterText())
        .email(p.getEmail())
        .phone(p.getPhone())
        .linkedin(p.getLinkedin())
        .linkedinDisplay(p.getLinkedinDisplay())
        .github(p.getGithub())
        .githubDisplay(p.getGithubDisplay())
        .facebook(p.getFacebook())
        .facebookDisplay(p.getFacebookDisplay())
        .xHandle(p.getXHandle())
        .xDisplay(p.getXDisplay())
        .instagram(p.getInstagram())
        .instagramDisplay(p.getInstagramDisplay())
        .build();
  }
}
