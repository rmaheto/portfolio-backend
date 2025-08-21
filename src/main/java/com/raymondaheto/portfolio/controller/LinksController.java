package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.ApiResponse;
import com.raymondaheto.portfolio.dto.LinksDto;
import com.raymondaheto.portfolio.dto.LinksUpdateDto;
import com.raymondaheto.portfolio.service.LinksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/links")
@RequiredArgsConstructor
public class LinksController {

  private final LinksService service;

  @GetMapping
  public ApiResponse<LinksDto> get() {
    return ApiResponse.ok(service.get());
  }

  @GetMapping("/both")
  public ApiResponse<LinksUpdateDto> getBoth() {
    return ApiResponse.ok(service.getBoth());
  }

  @PutMapping
  public ApiResponse<LinksUpdateDto> update(@RequestBody @Valid final LinksUpdateDto dto) {
    return ApiResponse.ok(service.update(dto));
  }
}
