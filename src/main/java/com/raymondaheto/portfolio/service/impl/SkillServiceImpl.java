package com.raymondaheto.portfolio.service.impl;

import com.raymondaheto.portfolio.dto.SkillCategoryWithItemsDto;
import com.raymondaheto.portfolio.entity.*;
import com.raymondaheto.portfolio.repositories.ProfileRepository;
import com.raymondaheto.portfolio.repositories.SkillCategoryRepository;
import com.raymondaheto.portfolio.repositories.SkillRepository;
import com.raymondaheto.portfolio.service.SkillService;
import java.text.Normalizer;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillServiceImpl implements SkillService {

  private final SkillCategoryRepository catRepo;
  private final SkillRepository skillRepo;
  private final ProfileRepository profileRepo;

  @Override
  @Transactional(readOnly = true)
  public List<SkillCategoryWithItemsDto> getAllGrouped() {
    try {
      final Profile p = requireProfile();
      return catRepo.findByProfileOrderBySortIndexAscIdAsc(p).stream()
          .map(
              c ->
                  SkillCategoryWithItemsDto.builder()
                      .id(c.getId())
                      .key(c.getKey())
                      .label(c.getLabel())
                      .sortIndex(c.getSortIndex())
                      .skills(
                          skillRepo.findByCategoryOrderBySortIndexAscIdAsc(c).stream()
                              .map(Skill::getName)
                              .toList())
                      .build())
          .toList();
    } catch (final RuntimeException ex) {
      log.error("Get grouped skills failed", ex);
      throw ex;
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<Skill> listByCategory(final String categoryKey) {
    try {
      final SkillCategory cat = findCategory(categoryKey);
      return skillRepo.findByCategoryOrderBySortIndexAscIdAsc(cat);
    } catch (final RuntimeException ex) {
      log.error("List skills failed cat={}", categoryKey, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public Skill create(final String name, final String categoryKey) {
    try {
      final SkillCategory cat = findOrCreateCategory(categoryKey);
      final int next = skillRepo.findMaxSortIndexByCategory(cat) + 1;
      return skillRepo.save(Skill.builder().category(cat).name(name).sortIndex(next).build());
    } catch (final RuntimeException ex) {
      log.error("Create skill failed cat={}, name={}", categoryKey, name, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public Skill update(final Long id, final String name) {
    try {
      final Skill s = skillRepo.findById(id).orElseThrow();
      s.setName(name);
      return skillRepo.save(s);
    } catch (final RuntimeException ex) {
      log.error("Update skill failed id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    try {
      skillRepo.deleteById(id);
    } catch (final RuntimeException ex) {
      log.error("Delete skill failed id={}", id, ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public void replaceCategory(final String categoryKey, List<String> names) {
    try {
      final SkillCategory cat = findOrCreateCategory(categoryKey);
      skillRepo.deleteByCategory(cat);
      names = names == null ? List.of() : names;
      for (int i = 0; i < names.size(); i++) {
        String n = names.get(i);
        if (n == null || n.isBlank()) {
          continue;
        }
        skillRepo.save(Skill.builder().category(cat).name(n.trim()).sortIndex(i).build());
      }
    } catch (final RuntimeException ex) {
      log.error("Replace category failed cat={}", categoryKey, ex);
      throw ex;
    }
  }

  private SkillCategory findCategory(final String key) {
    Profile p = requireProfile();
    return catRepo
        .findByProfileAndKeyIgnoreCase(p, key)
        .orElseThrow(() -> new NoSuchElementException("Category not found: " + key));
  }

  private static String slugify(final String input) {
    if (input == null) return "category";
    final String s =
        Normalizer.normalize(input, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "") // strip accents
            .toLowerCase(Locale.ROOT)
            .trim()
            .replace('_', ' ') // treat underscores as spaces
            .replaceAll("\\s+", "-") // collapse whitespace to hyphen
            .replaceAll("[^a-z0-9-]", "-") // non url-safe -> hyphen
            .replaceAll("-{2,}", "-") // collapse multiple hyphens
            .replaceAll("^-|-$", ""); // trim leading/trailing hyphens
    return s.isEmpty() ? "category" : s;
  }

  private SkillCategory findOrCreateCategory(final String rawKey) {
    final String key = slugify(rawKey);
    final Profile p =
        profileRepo.findFirstByOrderByIdAsc().orElseGet(() -> profileRepo.save(new Profile()));

    return catRepo
        .findByProfileAndKeyIgnoreCase(p, key)
        .orElseGet(
            () -> {
              final int nextIdx = catRepo.findByProfileOrderBySortIndexAscIdAsc(p).size();
              final String label =
                  (rawKey == null || rawKey.isBlank()) ? "Category" : rawKey.trim();
              return catRepo.save(
                  SkillCategory.builder()
                      .profile(p)
                      .key(key)
                      .label(label)
                      .sortIndex(nextIdx)
                      .build());
            });
  }

  private Profile requireProfile() {
    return profileRepo
        .findFirstByOrderByIdAsc()
        .orElseThrow(() -> new IllegalStateException("Profile not set up yet"));
  }
}
