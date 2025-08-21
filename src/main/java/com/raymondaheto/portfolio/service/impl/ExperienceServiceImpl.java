package com.raymondaheto.portfolio.service.impl;

import com.raymondaheto.portfolio.dto.ExperienceDto;
import com.raymondaheto.portfolio.entity.Experience;
import com.raymondaheto.portfolio.entity.Profile;
import com.raymondaheto.portfolio.repositories.ExperienceRepository;
import com.raymondaheto.portfolio.repositories.ProfileRepository;
import com.raymondaheto.portfolio.service.ExperienceService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExperienceServiceImpl implements ExperienceService {

  private final ProfileRepository profileRepo;
  private final ExperienceRepository expRepo;

  @Override
  @Transactional(readOnly = true)
  public List<ExperienceDto> list() {
    try {
      return expRepo.findAll().stream().map(this::toDto).toList();
    } catch (RuntimeException ex) {
      log.error("List experience failed", ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public ExperienceDto create(final ExperienceDto dto) {
    try {
      final Profile profile =
          profileRepo.findFirstByOrderByIdAsc().orElseGet(() -> profileRepo.save(new Profile()));
      final int nextIdx = profile.getExperiences().size();
      Experience e =
          Experience.builder()
              .profile(profile)
              .role(dto.getRole())
              .company(dto.getCompany())
              .period(dto.getPeriod())
              .bullets(
                  dto.getBullets() != null ? new ArrayList<>(dto.getBullets()) : new ArrayList<>())
              .stack(dto.getStack() != null ? new ArrayList<>(dto.getStack()) : new ArrayList<>())
              .sortIndex(dto.getSortIndex() != null ? dto.getSortIndex() : nextIdx)
              .build();
      e = expRepo.save(e);
      return toDto(e);
    } catch (final RuntimeException ex) {
      log.error("Create experience failed", ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public ExperienceDto update(final Long id, final ExperienceDto dto) {
    try {
      Experience e = expRepo.findById(id).orElseThrow();
      e.setRole(dto.getRole());
      e.setCompany(dto.getCompany());
      e.setPeriod(dto.getPeriod());
      e.setBullets(
          dto.getBullets() != null ? new ArrayList<>(dto.getBullets()) : new ArrayList<>());
      e.setStack(dto.getStack() != null ? new ArrayList<>(dto.getStack()) : new ArrayList<>());
      if (dto.getSortIndex() != null) {
        e.setSortIndex(dto.getSortIndex());
      }
      return toDto(expRepo.save(e));
    } catch (final RuntimeException ex) {
      log.error("Update experience failed id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    try {
      expRepo.deleteById(id);
    } catch (final RuntimeException ex) {
      log.error("Delete experience failed id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public void reorder(final List<Long> ids) {
    try {
      for (int i = 0; i < ids.size(); i++) {
        final Experience e = expRepo.findById(ids.get(i)).orElseThrow();
        e.setSortIndex(i);
      }
    } catch (final RuntimeException ex) {
      log.error("Reorder experience failed", ex);
      throw ex;
    }
  }

  private ExperienceDto toDto(final Experience e) {
    return ExperienceDto.builder()
        .id(e.getId())
        .role(e.getRole())
        .company(e.getCompany())
        .period(e.getPeriod())
        .bullets(e.getBullets())
        .stack(e.getStack())
        .sortIndex(e.getSortIndex())
        .build();
  }
}
