package com.rakuten.cep.points.dto;


import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Value
public class OrderDTO {

  private Integer id;

  @NotNull
  @Email
  private String customerEmail;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDateTime createdAt;

  @NotEmpty
  private List<@Valid OrderItemDTO> items;
}
