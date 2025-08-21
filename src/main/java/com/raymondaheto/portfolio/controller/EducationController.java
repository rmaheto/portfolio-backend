package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.ApiResponse;
import com.raymondaheto.portfolio.dto.EducationDto;
import com.raymondaheto.portfolio.service.EducationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/education")
@RequiredArgsConstructor
public class EducationController {

  private final EducationService educationService;

  @GetMapping
  public ApiResponse<List<EducationDto>> list() {
    return ApiResponse.ok(educationService.list());
  }

  @GetMapping("/{id}")
  public ApiResponse<EducationDto> get(@PathVariable final Long id) {
    return ApiResponse.ok(educationService.getById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ApiResponse<EducationDto> create(@RequestBody @Valid final EducationDto dto) {
    return ApiResponse.created(educationService.create(dto));
  }

  @PutMapping("/{id}")
  public ApiResponse<EducationDto> update(
      @PathVariable final Long id, @RequestBody @Valid final EducationDto dto) {
    return ApiResponse.ok(educationService.update(id, dto));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable final Long id) {
    educationService.delete(id);
  }

  @PatchMapping("/reorder")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void reorder(@RequestBody final List<Long> ids) {
    educationService.reorderEducation(ids);
  }
}
