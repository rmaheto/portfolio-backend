package com.raymondaheto.portfolio.service.impl;

import com.raymondaheto.portfolio.dto.SkillCategoryDto;
import com.raymondaheto.portfolio.dto.SkillCategoryWithItemsDto;
import com.raymondaheto.portfolio.entity.Profile;
import com.raymondaheto.portfolio.entity.Skill;
import com.raymondaheto.portfolio.entity.SkillCategory;
import com.raymondaheto.portfolio.repositories.ProfileRepository;
import com.raymondaheto.portfolio.repositories.SkillCategoryRepository;
import com.raymondaheto.portfolio.repositories.SkillRepository;
import com.raymondaheto.portfolio.service.SkillCategoryService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillCategoryServiceImpl implements SkillCategoryService {

  private final SkillCategoryRepository catRepo;
  private final SkillRepository skillRepo;
  private final ProfileRepository profileRepo;

  @Override
  @Transactional(readOnly = true)
  public List<SkillCategoryDto> list() {
    try {
      Profile p = requireProfile();
      return catRepo.findByProfileOrderBySortIndexAscIdAsc(p).stream().map(this::toDto).toList();
    } catch (final RuntimeException ex) {
      log.error("List skill categories failed", ex);
      throw ex;
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<SkillCategoryWithItemsDto> listWithItems() {
    try {
      Profile p = requireProfile();
      return catRepo.findByProfileOrderBySortIndexAscIdAsc(p).stream()
          .map(
              c -> {
                final List<String> items =
                    skillRepo.findByCategoryOrderBySortIndexAscIdAsc(c).stream()
                        .map(Skill::getName)
                        .toList();
                return SkillCategoryWithItemsDto.builder()
                    .id(c.getId())
                    .key(c.getKey())
                    .label(c.getLabel())
                    .sortIndex(c.getSortIndex())
                    .skills(items)
                    .build();
              })
          .toList();
    } catch (final RuntimeException ex) {
      log.error("List categories with items failed", ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public SkillCategoryDto create(final String key, final String label, final Integer sortIndex) {
    try {
      Profile p = ensureProfile();
      String k = normalize(key);
      if (catRepo.existsByProfileAndKeyIgnoreCase(p, k)) {
        throw new IllegalArgumentException("Category key already exists: " + k);
      }

      int idx =
          sortIndex != null ? sortIndex : catRepo.findByProfileOrderBySortIndexAscIdAsc(p).size();

      SkillCategory c =
          SkillCategory.builder().profile(p).key(k).label(label).sortIndex(idx).build();
      return toDto(catRepo.save(c));
    } catch (final RuntimeException ex) {
      log.error("Create category failed key='{}'", key, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public SkillCategoryDto update(
      final Long id, final String key, final String label, final Integer sortIndex) {
    try {
      SkillCategory c = catRepo.findById(id).orElseThrow();
      if (key != null) {
        c.setKey(normalize(key));
      }
      if (label != null) {
        c.setLabel(label);
      }
      if (sortIndex != null) {
        c.setSortIndex(sortIndex);
      }
      return toDto(catRepo.save(c));
    } catch (final RuntimeException ex) {
      log.error("Update category failed id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    try {
      catRepo.deleteById(id);
    } catch (final RuntimeException ex) {
      log.error("Delete category failed id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public void reorder(final List<Long> ids) {
    try {
      for (int i = 0; i < ids.size(); i++) {
        final SkillCategory c = catRepo.findById(ids.get(i)).orElseThrow();
        c.setSortIndex(i);
      }
    } catch (final RuntimeException ex) {
      log.error("Reorder categories failed", ex);
      throw ex;
    }
  }

  /* helpers */
  private SkillCategoryDto toDto(final SkillCategory c) {
    return SkillCategoryDto.builder()
        .id(c.getId())
        .key(c.getKey())
        .label(c.getLabel())
        .sortIndex(c.getSortIndex())
        .build();
  }

  private String normalize(final String key) {
    return key.trim().toLowerCase(Locale.ROOT).replace(' ', '-');
  }

  private Profile ensureProfile() {
    return profileRepo.findFirstByOrderByIdAsc().orElseGet(() -> profileRepo.save(new Profile()));
  }

  private Profile requireProfile() {
    return profileRepo
        .findFirstByOrderByIdAsc()
        .orElseThrow(() -> new IllegalStateException("Profile not set up yet"));
  }
}
