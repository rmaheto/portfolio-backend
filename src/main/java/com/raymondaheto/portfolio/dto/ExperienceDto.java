package com.raymondaheto.portfolio.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceDto {

  private Long id;
  private String role;
  private String company;
  private String period;
  private List<String> bullets;
  private List<String> stack;
  private Integer sortIndex;
}
