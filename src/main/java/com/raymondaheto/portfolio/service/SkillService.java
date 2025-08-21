package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.dto.SkillCategoryWithItemsDto;
import com.raymondaheto.portfolio.entity.Skill;
import java.util.List;

public interface SkillService {
  List<SkillCategoryWithItemsDto> getAllGrouped();

  List<Skill> listByCategory(String categoryKey);

  Skill create(String name, String categoryKey);

  Skill update(Long id, String name);

  void delete(Long id);

  void replaceCategory(String categoryKey, java.util.List<String> namesInOrder);
}
