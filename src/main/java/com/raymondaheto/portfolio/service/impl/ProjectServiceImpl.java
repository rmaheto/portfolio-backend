package com.raymondaheto.portfolio.service.impl;

import com.raymondaheto.portfolio.dto.ProjectDto;
import com.raymondaheto.portfolio.entity.Profile;
import com.raymondaheto.portfolio.entity.Project;
import com.raymondaheto.portfolio.repositories.ProfileRepository;
import com.raymondaheto.portfolio.repositories.ProjectRepository;
import com.raymondaheto.portfolio.service.ProjectService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepo;
  private final ProfileRepository profileRepo;

  @Override
  @Transactional(readOnly = true)
  public List<ProjectDto> list() {
    try {
      return projectRepo.findAll().stream().map(this::toDto).toList();
    } catch (final RuntimeException ex) {
      log.error("Failed to list Projects", ex);
      throw ex;
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ProjectDto getById(final Long id) {
    try {
      final Project p =
          projectRepo
              .findById(id)
              .orElseThrow(() -> new NoSuchElementException("Project not found"));
      return toDto(p);
    } catch (final RuntimeException ex) {
      log.error("Failed to get Project id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public ProjectDto create(final ProjectDto dto) {
    try {
      final Profile profile = ensureProfile();
      final Project p = new Project();
      p.setProfile(profile);
      p.setName(dto.getName());
      p.setDescription(dto.getDesc());
      p.setTags(dto.getTags() != null ? new ArrayList<>(dto.getTags()) : new ArrayList<>());
      final Project saved = projectRepo.save(p);
      return toDto(saved);
    } catch (final RuntimeException ex) {
      log.error("Failed to create Project with name='{}'", dto != null ? dto.getName() : null, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public ProjectDto update(final Long id, final ProjectDto dto) {
    try {
      final Project p =
          projectRepo
              .findById(id)
              .orElseThrow(() -> new NoSuchElementException("Project not found"));
      p.setName(dto.getName());
      p.setDescription(dto.getDesc());
      p.setTags(dto.getTags() != null ? new ArrayList<>(dto.getTags()) : new ArrayList<>());
      final Project saved = projectRepo.save(p);
      return toDto(saved);
    } catch (final RuntimeException ex) {
      log.error("Failed to update Project id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    try {
      projectRepo.deleteById(id);
    } catch (final RuntimeException ex) {
      log.error("Failed to delete Project id={}", id, ex);
      throw ex;
    }
  }

  private Profile ensureProfile() {
    // Create a holder profile if none exists yet so sections can be added first.
    return profileRepo.findAll().stream()
        .findFirst()
        .orElseGet(() -> profileRepo.save(new Profile()));
  }

  private ProjectDto toDto(final Project p) {
    return ProjectDto.builder()
        .id(p.getId())
        .name(p.getName())
        .desc(p.getDescription())
        .tags(p.getTags())
        .build();
  }
}
