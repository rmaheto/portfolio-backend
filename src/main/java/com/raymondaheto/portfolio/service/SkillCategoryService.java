package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.dto.SkillCategoryDto;
import com.raymondaheto.portfolio.dto.SkillCategoryWithItemsDto;
import java.util.List;

public interface SkillCategoryService {

  List<SkillCategoryDto> list();

  List<SkillCategoryWithItemsDto> listWithItems(); // grouped payload for UI

  SkillCategoryDto create(String key, String label, Integer sortIndex);

  SkillCategoryDto update(Long id, String key, String label, Integer sortIndex);

  void delete(Long id);

  void reorder(List<Long> idsInOrder);
}
