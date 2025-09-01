package com.raymondaheto.portfolio.service.impl;

import com.raymondaheto.portfolio.dto.RecaptchaResponse;
import jakarta.annotation.Nonnull;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CaptchaService {

  @Value("${recaptcha.secretKey}")
  private final String recaptchaSecret;

  @Value("${recaptcha.verifyUrl}")
  private final String recaptchaVerifyUrl;

  private final RestTemplate restTemplate;

  private final RetryTemplate retryTemplate;

  public boolean verifyToken(@Nonnull final String token) {

    try {
      final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
      requestBody.add("secret", recaptchaSecret);
      requestBody.add("response", token);

      final HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      final HttpEntity<MultiValueMap<String, String>> request =
          new HttpEntity<>(requestBody, headers);

      return retryTemplate.execute(
          context -> {
            final ResponseEntity<RecaptchaResponse> response =
                restTemplate.postForEntity(recaptchaVerifyUrl, request, RecaptchaResponse.class);

            return response.getStatusCode() == HttpStatus.OK
                && Objects.requireNonNull(response.getBody()).isSuccess();
          });
    } catch (final Exception e) {
      log.warn("Exception thrown while trying to verify recaptcha token", e);
      return false;
    }
  }
}
