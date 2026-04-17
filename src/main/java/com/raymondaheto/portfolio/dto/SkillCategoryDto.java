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
public class SkillCategoryDto {

  private Long id;
  private String key;
  private String label;
  private Integer sortIndex;
}
