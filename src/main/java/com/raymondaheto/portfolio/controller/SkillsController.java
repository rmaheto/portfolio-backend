package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.ApiResponse;
import com.raymondaheto.portfolio.dto.SkillCategoryWithItemsDto;
import com.raymondaheto.portfolio.dto.SkillDto;
import com.raymondaheto.portfolio.entity.Skill;
import com.raymondaheto.portfolio.service.SkillService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillsController {

  private final SkillService service;

  @GetMapping("/grouped")
  public ApiResponse<SkillDto> grouped() {
    final List<SkillCategoryWithItemsDto> categories = service.getAllGrouped();
    return ApiResponse.ok(SkillDto.builder().categories(categories).build());
  }

  @GetMapping("/{categoryKey}")
  public ApiResponse<List<Skill>> list(@PathVariable final String categoryKey) {
    return ApiResponse.ok(service.listByCategory(categoryKey));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ApiResponse<Skill> create(
      @RequestParam final String name, @RequestParam final String categoryKey) {
    return ApiResponse.created(service.create(name, categoryKey));
  }

  @PutMapping("/{id}")
  public ApiResponse<Skill> update(@PathVariable final Long id, @RequestParam final String name) {
    return ApiResponse.ok(service.update(id, name));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable final Long id) {
    service.delete(id);
  }

  @PutMapping("/replace/{categoryKey}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void replace(
      @PathVariable final String categoryKey, @RequestBody @Valid final List<String> namesInOrder) {
    service.replaceCategory(categoryKey, namesInOrder);
  }
}
