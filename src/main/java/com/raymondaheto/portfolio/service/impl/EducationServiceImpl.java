package com.raymondaheto.portfolio.service.impl;

import com.raymondaheto.portfolio.dto.EducationDto;
import com.raymondaheto.portfolio.entity.Education;
import com.raymondaheto.portfolio.entity.Profile;
import com.raymondaheto.portfolio.repositories.EducationRepository;
import com.raymondaheto.portfolio.repositories.ProfileRepository;
import com.raymondaheto.portfolio.service.EducationService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationServiceImpl implements EducationService {

  private final EducationRepository educationRepo;
  private final ProfileRepository profileRepo;

  @Override
  @Transactional(readOnly = true)
  public List<EducationDto> list() {
    try {
      return educationRepo.findAll().stream().map(this::toDto).toList();
    } catch (final RuntimeException ex) {
      log.error("Failed to list Education entries", ex);
      throw ex;
    }
  }

  @Override
  @Transactional(readOnly = true)
  public EducationDto getById(final Long id) {
    try {
      final Education e =
          educationRepo
              .findById(id)
              .orElseThrow(() -> new NoSuchElementException("Education not found"));
      return toDto(e);
    } catch (final RuntimeException ex) {
      log.error("Failed to get Education id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public EducationDto create(final EducationDto dto) {
    try {
      final Profile profile = ensureProfile();
      final int nextIdx = profile.getEducation().size(); // append at end
      Education e =
          Education.builder()
              .profile(profile)
              .name(dto.getName())
              .school(dto.getSchool())
              .years(dto.getYears())
              .sortIndex(dto.getSortIndex() != null ? dto.getSortIndex() : nextIdx)
              .build();
      e = educationRepo.save(e);
      return toDto(e);
    } catch (final RuntimeException ex) {
      log.error("Failed to create Education '{}'", dto != null ? dto.getName() : null, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public EducationDto update(final Long id, final EducationDto dto) {
    try {
      Education e = educationRepo.findById(id).orElseThrow();
      e.setName(dto.getName());
      e.setSchool(dto.getSchool());
      e.setYears(dto.getYears());
      if (dto.getSortIndex() != null) e.setSortIndex(dto.getSortIndex());
      e = educationRepo.save(e);
      return toDto(e);
    } catch (final RuntimeException ex) {
      log.error("Failed to update Education id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    try {
      educationRepo.deleteById(id);
    } catch (final RuntimeException ex) {
      log.error("Failed to delete Education id={}", id, ex);
      throw ex;
    }
  }

  @Transactional
  public void reorderEducation(final List<Long> idsInOrder) {
    for (int i = 0; i < idsInOrder.size(); i++) {
      final Education e = educationRepo.findById(idsInOrder.get(i)).orElseThrow();
      e.setSortIndex(i);
    }
  }

  private Profile ensureProfile() {
    return profileRepo.findAll().stream()
        .findFirst()
        .orElseGet(() -> profileRepo.save(new Profile()));
  }

  private EducationDto toDto(final Education e) {
    return EducationDto.builder()
        .id(e.getId())
        .name(e.getName())
        .school(e.getSchool())
        .years(e.getYears())
        .build();
  }
}
