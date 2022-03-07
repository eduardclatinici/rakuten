package com.rakuten.cep.campaign.core.dto;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class CampaignDTO {

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @NotNull
    @DecimalMin(value = "1.0", inclusive = false)
    private BigDecimal points;

    @NotNull
    @Size(min = 1)
    Set<@Valid ProductDTO> products;
}
