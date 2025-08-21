package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.ApiResponse;
import com.raymondaheto.portfolio.dto.ProjectDto;
import com.raymondaheto.portfolio.service.ProjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @GetMapping
  public ApiResponse<List<ProjectDto>> list() {
    return ApiResponse.ok(projectService.list());
  }

  @GetMapping("/{id}")
  public ApiResponse<ProjectDto> get(@PathVariable final Long id) {
    return ApiResponse.ok(projectService.getById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ApiResponse<ProjectDto> create(@RequestBody @Valid final ProjectDto dto) {
    return ApiResponse.created(projectService.create(dto));
  }

  @PutMapping("/{id}")
  public ApiResponse<ProjectDto> update(
      @PathVariable final Long id, @RequestBody @Valid final ProjectDto dto) {
    return ApiResponse.ok(projectService.update(id, dto));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable final Long id) {
    projectService.delete(id);
  }
}
