package com.rakuten.cep.points.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;


@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class ProductDTO {

  @NotNull
  private int id;

  private String name;

  private BigDecimal price;
}
