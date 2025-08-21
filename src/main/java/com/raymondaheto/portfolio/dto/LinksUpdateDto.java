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
public class LinksUpdateDto {

  private LinksDto links;
  private LinksDto linksDisplay;
  private String contactIntro;
  private String footerText;
}
