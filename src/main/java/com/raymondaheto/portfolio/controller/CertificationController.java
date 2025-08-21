package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.ApiResponse;
import com.raymondaheto.portfolio.dto.CertDto;
import com.raymondaheto.portfolio.service.CertificationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/certifications")
@RequiredArgsConstructor
public class CertificationController {

  private final CertificationService service;

  @GetMapping
  public ApiResponse<List<CertDto>> list() {
    return ApiResponse.ok(service.list());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ApiResponse<CertDto> create(@RequestBody @Valid final CertDto dto) {
    return ApiResponse.created(service.create(dto));
  }

  @PutMapping("/{id}")
  public ApiResponse<CertDto> update(
      @PathVariable final Long id, @RequestBody @Valid final CertDto dto) {
    return ApiResponse.ok(service.update(id, dto));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable final Long id) {
    service.delete(id);
  }
}
