package com.rakuten.cep.points.service;


import com.rakuten.cep.points.domain.Customer;
import com.rakuten.cep.points.dto.OrderItemProjection;
import com.rakuten.cep.points.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;


@Service
class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;


  Customer getCustomerByEmail (String email) {

    return customerRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Customer not found for email: " + email
        ));
  }


  Customer update (Customer customer) {

    return customerRepository.save(customer);
  }


  public void updatePointsBalanceBy (Integer orderId, List<OrderItemProjection> orderItems) {

    customerRepository.updatePointsBalanceBy(orderId, getDeletedPoints(orderItems));
  }


  public void updatePointsBalanceForDeletedItems (
      Integer orderId,
      List<OrderItemProjection> orderItems
  ) {

    customerRepository.updatePointsBalanceBy(orderId, getDeletedPoints(orderItems));
  }


  private BigDecimal getDeletedPoints (List<OrderItemProjection> orderItems) {

    return orderItems.stream()
        .map(orderItem -> new BigDecimal(orderItem.getQuantity()).multiply(orderItem.getPrice())
            .multiply(orderItem.getRate()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
