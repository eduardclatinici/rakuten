package com.rakuten.cep.points.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

  @Id
  @SequenceGenerator(name = "customer_seq", sequenceName = "customer_sequence")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
  private Integer id;

  @Column(nullable = false)
  @Email
  private String email;

  @Column(name = "points_balance", nullable = false, columnDefinition = "numeric default 0")
  private BigDecimal pointsBalance;
}
