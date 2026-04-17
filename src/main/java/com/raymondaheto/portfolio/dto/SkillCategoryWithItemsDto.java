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
public class SkillCategoryWithItemsDto {

  private Long id;
  private String key;
  private String label;
  private Integer sortIndex;
  private List<String> skills;
}
