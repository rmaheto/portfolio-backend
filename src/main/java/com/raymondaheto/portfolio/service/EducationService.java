package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.dto.EducationDto;
import java.util.List;

public interface EducationService {

  List<EducationDto> list();

  EducationDto getById(Long id);

  EducationDto create(EducationDto dto);

  EducationDto update(Long id, EducationDto dto);

  void delete(Long id);

  void reorderEducation(final List<Long> idsInOrder);
}
