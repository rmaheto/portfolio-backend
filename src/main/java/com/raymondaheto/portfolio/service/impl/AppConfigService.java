package com.raymondaheto.portfolio.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppConfigService {

  @Value("${recaptcha.enabled:true}")
  private boolean recaptchaEnabled;

  public boolean isRecaptchaEnabled() {
    return recaptchaEnabled;
  }
}
