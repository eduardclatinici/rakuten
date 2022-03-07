package com.rakuten.cep.points.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;


@Entity(name = "order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

  @Id
  @SequenceGenerator(name = "order_item_seq", sequenceName = "order_item_sequence")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
  private Integer id;

  @Column(nullable = false)
  @Embedded
  private Product product;

  @Column(nullable = false)
  @Min(1)
  private Integer quantity;

  @Column(nullable = false)
  @Min(1)
  private BigDecimal rate;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
}