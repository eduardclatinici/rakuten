package com.rakuten.cep.points.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Product {

  @Column(name = "product_id", nullable = false)
  private Integer productId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private BigDecimal price;
}
