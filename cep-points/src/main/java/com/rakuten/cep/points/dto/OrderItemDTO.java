package com.rakuten.cep.points.dto;


import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Value
public class OrderItemDTO {

  private Integer id;

  @NotNull
  private @Valid
  ProductDTO product;

  @NotNull
  @Min(1)
  private int quantity;
}
