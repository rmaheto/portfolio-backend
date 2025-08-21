package com.raymondaheto.portfolio.dto;

import com.raymondaheto.portfolio.entity.SkillCategoryEnum;
import jakarta.validation.constraints.NotNull;
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
public class SkillReplaceDto {

  @NotNull private SkillCategoryEnum category;
  private List<String> names;
}
