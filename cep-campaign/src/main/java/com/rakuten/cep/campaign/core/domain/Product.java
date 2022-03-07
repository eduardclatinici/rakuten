package com.rakuten.cep.campaign.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Product {

    @Id
    @SequenceGenerator(name = "product_seq", sequenceName = "product_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToMany(mappedBy = "products")
    private Set<Campaign> campaigns;
}
