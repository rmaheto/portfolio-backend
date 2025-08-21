package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.dto.ExperienceDto;
import java.util.List;

public interface ExperienceService {

  List<ExperienceDto> list();

  ExperienceDto create(ExperienceDto dto);

  ExperienceDto update(Long id, ExperienceDto dto);

  void delete(Long id);

  void reorder(List<Long> idsInOrder);
}
