package com.raymondaheto.portfolio.model;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenData {
  private String username;
  private String firstName;
  private String lastName;
  private Set<String> roles;
  private Long schoolId;
}
