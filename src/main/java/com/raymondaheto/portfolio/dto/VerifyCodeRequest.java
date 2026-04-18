package com.raymondaheto.portfolio.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyCodeRequest(@NotBlank String username, @NotBlank String code) {}
