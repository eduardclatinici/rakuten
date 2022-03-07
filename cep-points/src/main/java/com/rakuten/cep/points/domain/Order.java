package com.rakuten.cep.points.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Order {

  @Id
  @SequenceGenerator(name = "order_seq", sequenceName = "order_sequence")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
  private Integer id;

  @JoinColumn(name = "customer_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "order_items", nullable = false)
  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;
}
