package com.rakuten.cep.points.dto;


import lombok.Value;

import javax.validation.constraints.NotNull;


@Value
public class PointsTransactionDTO {

  private Integer id;

  @NotNull
  private OrderDTO order;
}
