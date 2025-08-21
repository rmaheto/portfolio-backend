package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.ApiResponse;
import com.raymondaheto.portfolio.dto.ExperienceDto;
import com.raymondaheto.portfolio.dto.ExperienceOrderDto;
import com.raymondaheto.portfolio.service.ExperienceService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/experience")
@RequiredArgsConstructor
public class ExperienceController {

  private final ExperienceService service;

  @GetMapping
  public ApiResponse<List<ExperienceDto>> list() {
    return ApiResponse.ok(service.list());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ApiResponse<ExperienceDto> create(@RequestBody @Valid final ExperienceDto dto) {
    return ApiResponse.created(service.create(dto));
  }

  @PutMapping("/{id}")
  public ApiResponse<ExperienceDto> update(
      @PathVariable final Long id, @RequestBody @Valid final ExperienceDto dto) {
    return ApiResponse.ok(service.update(id, dto));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable final Long id) {
    service.delete(id);
  }

  @PutMapping("/reorder")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void reorder(@RequestBody @Valid final ExperienceOrderDto body) {
    service.reorder(body.getOrderedIds());
  }
}
