package com.raymondaheto.portfolio.dto;

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
public class EducationDto {

  private Long id;
  private String name;
  private String school;
  private String years;
  private Integer sortIndex;
}
