package com.raymondaheto.portfolio.model;

public record AuthResponse(String jwt, boolean mustChangePassword) {}
