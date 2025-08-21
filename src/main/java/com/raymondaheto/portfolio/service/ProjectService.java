package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.dto.ProjectDto;
import java.util.List;

public interface ProjectService {

  List<ProjectDto> list();

  ProjectDto getById(Long id);

  ProjectDto create(ProjectDto dto);

  ProjectDto update(Long id, ProjectDto dto);

  void delete(Long id);
}
