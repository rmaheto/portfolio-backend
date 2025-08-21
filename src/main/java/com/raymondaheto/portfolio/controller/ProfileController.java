package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.ApiResponse;
import com.raymondaheto.portfolio.dto.ProfileDto;
import com.raymondaheto.portfolio.service.ProfileQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileQueryService service;

  @GetMapping
  public ApiResponse<ProfileDto> get() {

    return ApiResponse.ok(service.get());
  }
}
