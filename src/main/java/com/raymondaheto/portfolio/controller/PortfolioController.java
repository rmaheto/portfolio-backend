package com.raymondaheto.portfolio.controller;

import com.raymondaheto.portfolio.dto.PortfolioResponseDto;
import com.raymondaheto.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

  private final PortfolioService portfolioService;

  @GetMapping
  public ResponseEntity<PortfolioResponseDto> getPortfolio() {
    return ResponseEntity.ok(portfolioService.getPortfolio());
  }
}
