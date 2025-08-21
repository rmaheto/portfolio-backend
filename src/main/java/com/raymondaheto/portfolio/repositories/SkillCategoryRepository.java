package com.raymondaheto.portfolio.repositories;

import com.raymondaheto.portfolio.entity.Profile;
import com.raymondaheto.portfolio.entity.SkillCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillCategoryRepository extends JpaRepository<SkillCategory, Long> {
  List<SkillCategory> findByProfileOrderBySortIndexAscIdAsc(Profile profile);

  Optional<SkillCategory> findByProfileAndKeyIgnoreCase(Profile profile, String key);

  boolean existsByProfileAndKeyIgnoreCase(Profile profile, String key);
}
