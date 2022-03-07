package com.rakuten.cep.campaign.core.dto;

import lombok.Value;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@Value
public class ProductDTO {

    @NotNull
    private int id;

    private String name;

    private BigDecimal price;
}
