package com.raymondaheto.portfolio.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull private String code;

  @NotNull
  @Pattern(regexp = "^[a-z]{3}-[A-Z]{3}$")
  private String locale;

  @NotNull private String localizedMessage;

  private List<String> paths;

  @NotNull private Severity severity;

  private String supportInformation;

  public enum Severity {
    INFO,
    WARNING,
    ERROR
  }
}
