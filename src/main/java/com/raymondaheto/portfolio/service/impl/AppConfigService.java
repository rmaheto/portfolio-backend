package com.raymondaheto.portfolio.service.impl;


import com.raymondaheto.portfolio.entity.AppConfig;
import com.raymondaheto.portfolio.repositories.AppConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppConfigService {
  private final AppConfigRepository configRepo;

  public boolean isRecaptchaEnabled() {
    return Boolean.parseBoolean(
        configRepo.findByKey("enable_recaptcha").map(AppConfig::getValue).orElse("false"));
  }
}
