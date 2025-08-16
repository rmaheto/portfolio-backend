package com.raymondaheto.portfolio.model;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Errors implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull @Valid private List<Error> errors;
}
