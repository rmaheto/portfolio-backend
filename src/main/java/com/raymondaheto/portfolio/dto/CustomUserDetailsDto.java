package com.raymondaheto.portfolio.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomUserDetailsDto {
  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private Boolean mustChangePassword;
}
