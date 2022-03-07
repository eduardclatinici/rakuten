package com.rakuten.cep.points.rest;


import com.rakuten.cep.points.domain.Customer;
import com.rakuten.cep.points.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;


@Controller
@RequestMapping(path = "/pointsbalance")
public class PointsController {

  @Autowired
  CustomerRepository customerRepository;


  @GetMapping(path = "/customer/{customerEmail}")
  public ResponseEntity<BigDecimal> getCustomerPointsBalanceBy (
      @PathVariable("customerEmail") String customerEmail
  ) {

    return ResponseEntity.ok(customerRepository.findByEmail(customerEmail)
                                 .map(Customer::getPointsBalance)
                                 .orElseThrow(() -> new ResponseStatusException(
                                     HttpStatus.NOT_FOUND,
                                     "Customer not found for email: " + customerEmail
                                 )));
  }
}
