package com.raymondaheto.portfolio.model;

import jakarta.validation.constraints.*;

public record ContactRequest(
    @NotBlank @Size(max = 80) String name,
    @NotBlank @Email @Size(max = 120) String email,
    @NotBlank @Size(max = 120) String subject,
    @NotBlank @Size(max = 4000) String message,
    String captchaToken) {}
