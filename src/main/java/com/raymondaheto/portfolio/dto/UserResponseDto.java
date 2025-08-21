package com.raymondaheto.portfolio.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  private Long id;
  private String firstName;
  private String lastName;

  private String email;
  private String username;
  private String status;
  private LocalDateTime lastActiveAt;
}
