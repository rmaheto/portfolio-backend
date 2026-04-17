package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.PortfolioResponseDto.*;
import com.raymondaheto.portfolio.entity.*;
import com.raymondaheto.portfolio.service.PortfolioService;
import com.raymondaheto.portfolio.service.ResumeService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

  private final PortfolioService portfolioService;
  private final ResumeService resumeService;

  // ── Profile ───────────────────────────────────────────────────────────────

  @PutMapping("/profile")
  public ResponseEntity<ProfileDto> updateProfile(@RequestBody ProfileDto dto) {
    return ResponseEntity.ok(portfolioService.updateProfile(dto));
  }

  // ── Skills ────────────────────────────────────────────────────────────────

  @GetMapping("/skills")
  public ResponseEntity<List<Skill>> getSkills() {
    return ResponseEntity.ok(portfolioService.getSkills());
  }

  @PostMapping("/skills")
  public ResponseEntity<Skill> addSkill(@RequestBody Skill skill) {
    return ResponseEntity.ok(portfolioService.addSkill(skill));
  }

  @PutMapping("/skills/{id}")
  public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
    return ResponseEntity.ok(portfolioService.updateSkill(id, skill));
  }

  @DeleteMapping("/skills/{id}")
  public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
    portfolioService.deleteSkill(id);
    return ResponseEntity.noContent().build();
  }

  // ── Experience ────────────────────────────────────────────────────────────

  @GetMapping("/experience")
  public ResponseEntity<List<Experience>> getExperience() {
    return ResponseEntity.ok(portfolioService.getExperiences());
  }

  @PostMapping("/experience")
  public ResponseEntity<Experience> addExperience(@RequestBody Experience exp) {
    return ResponseEntity.ok(portfolioService.addExperience(exp));
  }

  @PutMapping("/experience/{id}")
  public ResponseEntity<Experience> updateExperience(
      @PathVariable Long id, @RequestBody Experience exp) {
    return ResponseEntity.ok(portfolioService.updateExperience(id, exp));
  }

  @DeleteMapping("/experience/{id}")
  public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
    portfolioService.deleteExperience(id);
    return ResponseEntity.noContent().build();
  }

  // ── Projects ──────────────────────────────────────────────────────────────

  @GetMapping("/projects")
  public ResponseEntity<List<Project>> getProjects() {
    return ResponseEntity.ok(portfolioService.getProjects());
  }

  @PostMapping("/projects")
  public ResponseEntity<Project> addProject(@RequestBody Project project) {
    return ResponseEntity.ok(portfolioService.addProject(project));
  }

  @PutMapping("/projects/{id}")
  public ResponseEntity<Project> updateProject(
      @PathVariable Long id, @RequestBody Project project) {
    return ResponseEntity.ok(portfolioService.updateProject(id, project));
  }

  @DeleteMapping("/projects/{id}")
  public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
    portfolioService.deleteProject(id);
    return ResponseEntity.noContent().build();
  }

  // ── Certifications ────────────────────────────────────────────────────────

  @GetMapping("/certifications")
  public ResponseEntity<List<Certification>> getCertifications() {
    return ResponseEntity.ok(portfolioService.getCertifications());
  }

  @PostMapping("/certifications")
  public ResponseEntity<Certification> addCertification(@RequestBody Certification cert) {
    return ResponseEntity.ok(portfolioService.addCertification(cert));
  }

  @PutMapping("/certifications/{id}")
  public ResponseEntity<Certification> updateCertification(
      @PathVariable Long id, @RequestBody Certification cert) {
    return ResponseEntity.ok(portfolioService.updateCertification(id, cert));
  }

  @DeleteMapping("/certifications/{id}")
  public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
    portfolioService.deleteCertification(id);
    return ResponseEntity.noContent().build();
  }

  // ── Education ─────────────────────────────────────────────────────────────

  @GetMapping("/education")
  public ResponseEntity<List<Education>> getEducation() {
    return ResponseEntity.ok(portfolioService.getEducation());
  }

  @PostMapping("/education")
  public ResponseEntity<Education> addEducation(@RequestBody Education edu) {
    return ResponseEntity.ok(portfolioService.addEducation(edu));
  }

  @PutMapping("/education/{id}")
  public ResponseEntity<Education> updateEducation(
      @PathVariable Long id, @RequestBody Education edu) {
    return ResponseEntity.ok(portfolioService.updateEducation(id, edu));
  }

  @DeleteMapping("/education/{id}")
  public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
    portfolioService.deleteEducation(id);
    return ResponseEntity.noContent().build();
  }

  // ── Resume ────────────────────────────────────────────────────────────────

  @PostMapping("/resume")
  public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file)
      throws IOException {
    String url = resumeService.upload(file);
    return ResponseEntity.ok(url);
  }
}
