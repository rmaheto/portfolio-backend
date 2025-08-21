package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.ApiResponse;
import com.raymondaheto.portfolio.dto.SkillCategoryDto;
import com.raymondaheto.portfolio.dto.SkillCategoryWithItemsDto;
import com.raymondaheto.portfolio.service.SkillCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/skill-categories")
@RequiredArgsConstructor
public class SkillCategoryController {

  private final SkillCategoryService skillCategoryService;

  @GetMapping
  public ApiResponse<List<SkillCategoryDto>> list() {
    return ApiResponse.ok(skillCategoryService.list());
  }

  @GetMapping("/with-items")
  public ApiResponse<List<SkillCategoryWithItemsDto>> listWithItems() {
    return ApiResponse.ok(skillCategoryService.listWithItems());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ApiResponse<SkillCategoryDto> create(
      @RequestParam final String key,
      @RequestParam final String label,
      @RequestParam(required = false) final Integer sortIndex) {
    return ApiResponse.created(skillCategoryService.create(key, label, sortIndex));
  }

  @PutMapping("/{id}")
  public ApiResponse<SkillCategoryDto> update(
      @PathVariable final Long id,
      @RequestParam(required = false) final String key,
      @RequestParam(required = false) final String label,
      @RequestParam(required = false) final Integer sortIndex) {
    return ApiResponse.ok(skillCategoryService.update(id, key, label, sortIndex));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable final Long id) {
    skillCategoryService.delete(id);
  }

  @PatchMapping("/reorder")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void reorder(@RequestBody final List<Long> ids) {
    skillCategoryService.reorder(ids);
  }
}
