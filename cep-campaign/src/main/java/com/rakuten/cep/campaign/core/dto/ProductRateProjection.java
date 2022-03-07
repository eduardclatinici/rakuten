package com.rakuten.cep.campaign.core.dto;

import java.math.BigDecimal;

public interface ProductRateProjection {

    int getId();

    String getName();

    BigDecimal getPrice();

    BigDecimal getRate();
}
