package com.raymondaheto.portfolio.repositories;

import com.raymondaheto.portfolio.entity.Skill;
import com.raymondaheto.portfolio.entity.SkillCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SkillRepository extends JpaRepository<Skill, Long> {

  List<Skill> findByCategoryOrderBySortIndexAscIdAsc(SkillCategory category);

  void deleteByCategory(SkillCategory category);

  @Query("select coalesce(max(s.sortIndex), -1) from Skill s where s.category = :cat")
  int findMaxSortIndexByCategory(@Param("cat") SkillCategory cat);
}
