package com.raymondaheto.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RecaptchaResponse {
  private boolean success;

  @JsonProperty("error-codes")
  private List<String> errorCodes;

  @JsonProperty("challenge_ts")
  private String challengeTs;

  private String hostname;
}
