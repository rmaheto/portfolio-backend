package com.raymondaheto.portfolio.exception;

import com.raymondaheto.portfolio.model.Error;
import com.raymondaheto.portfolio.model.Errors;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {


  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Errors> handleIllegalArgument(final IllegalArgumentException ex) {
    return build(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", ex.getMessage(), null);
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<Errors> handleInternalServerException(final InternalServerException ex) {
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "GENERIC_ERROR", ex.getMessage(), null);
  }


  @ExceptionHandler(RecaptchaException.class)
  public ResponseEntity<Errors> handleRecaptchaException(final RecaptchaException ex) {
    return build(HttpStatus.BAD_REQUEST, "RECAPTCHA_INVALID", ex.getMessage(), null);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Errors> handleOther(final Exception ex) {
    log.error("Unexpected error", ex);
    return build(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "GENERIC_ERROR",
        "An unexpected error occurred. Please contact support.",
        ex.getMessage());
  }

  private ResponseEntity<Errors> build(
      final HttpStatus status,
      final String code,
      final String localizedMessage,
      final String supportInformation) {
    final Error error =
        Error.builder()
            .code(code)
            .locale("eng-USA")
            .localizedMessage(localizedMessage)
            .severity(com.raymondaheto.portfolio.model.Error.Severity.ERROR)
            .supportInformation(supportInformation)
            .build();

    final Errors errors = Errors.builder().errors(List.of(error)).build();

    return ResponseEntity.status(status).body(errors);
  }
}

