package com.raymondaheto.portfolio.repository;

import com.raymondaheto.portfolio.entity.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
  List<Skill> findAllByOrderByCategoryAscDisplayOrderAsc();
}
