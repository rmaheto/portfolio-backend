package com.raymondaheto.portfolio.service.impl;

import com.raymondaheto.portfolio.dto.CertDto;
import com.raymondaheto.portfolio.entity.Certification;
import com.raymondaheto.portfolio.entity.Profile;
import com.raymondaheto.portfolio.repositories.CertificationRepository;
import com.raymondaheto.portfolio.repositories.ProfileRepository;
import com.raymondaheto.portfolio.service.CertificationService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificationServiceImpl implements CertificationService {

  private final ProfileRepository profileRepo;
  private final CertificationRepository certRepo;

  @Override
  @Transactional(readOnly = true)
  public List<CertDto> list() {
    try {
      return certRepo.findAll().stream().map(this::toDto).toList();
    } catch (final RuntimeException ex) {
      log.error("List certifications failed", ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public CertDto create(final CertDto dto) {
    try {
      final Profile profile =
          profileRepo.findFirstByOrderByIdAsc().orElseGet(() -> profileRepo.save(new Profile()));
      final int nextIdx = profile.getCertifications().size();
      final Certification c =
          Certification.builder()
              .profile(profile)
              .name(dto.getName())
              .badgeUrl(dto.getBadge())
              .link(dto.getLink())
              .sortIndex(dto.getSortIndex() != null ? dto.getSortIndex() : nextIdx)
              .build();
      return toDto(certRepo.save(c));
    } catch (final RuntimeException ex) {
      log.error("Create certification failed", ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public CertDto update(final Long id, final CertDto dto) {
    try {
      final Certification c = certRepo.findById(id).orElseThrow();
      c.setName(dto.getName());
      c.setBadgeUrl(dto.getBadge());
      c.setLink(dto.getLink());
      if (dto.getSortIndex() != null) {
        c.setSortIndex(dto.getSortIndex());
      }
      return toDto(certRepo.save(c));
    } catch (final RuntimeException ex) {
      log.error("Update certification failed id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    try {
      certRepo.deleteById(id);
    } catch (final RuntimeException ex) {
      log.error("Delete certification failed id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public void reorder(final List<Long> ids) {
    try {
      for (int i = 0; i < ids.size(); i++) {
        final Certification c = certRepo.findById(ids.get(i)).orElseThrow();
        c.setSortIndex(i);
      }
    } catch (final RuntimeException ex) {
      log.error("Reorder certifications failed", ex);
      throw ex;
    }
  }

  private CertDto toDto(final Certification c) {
    return CertDto.builder()
        .id(c.getId())
        .name(c.getName())
        .badge(c.getBadgeUrl())
        .link(c.getLink())
        .sortIndex(c.getSortIndex())
        .build();
  }
}
