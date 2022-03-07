package com.rakuten.cep.points.dto;


import java.math.BigDecimal;


public interface OrderItemProjection {

  BigDecimal getPrice ();


  BigDecimal getRate ();


  Integer getQuantity ();
}
