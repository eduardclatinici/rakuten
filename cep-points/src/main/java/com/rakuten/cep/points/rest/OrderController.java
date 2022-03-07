package com.rakuten.cep.points.rest;


import com.rakuten.cep.points.dto.OrderDTO;
import com.rakuten.cep.points.dto.PointsTransactionDTO;
import com.rakuten.cep.points.facade.OrderFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import javax.validation.Valid;


@RestController
@RequestMapping("/pointstransaction")
public class OrderController {

  @Autowired
  private OrderFacade orderFacade;


  @PostMapping
  public ResponseEntity<OrderDTO> createPointsTransaction (
      @RequestBody @Valid PointsTransactionDTO pointsTransactionDTO
  ) {

    return ResponseEntity.created(URI.create("/pointstransaction/"))
        .body(orderFacade.saveOrder(pointsTransactionDTO.getOrder()));
  }


  @DeleteMapping(path = "/order/{orderId}")
  public void deleteOrder (@PathVariable("orderId") Integer orderId) {

    orderFacade.deleteOrderBy(orderId);
  }


  @DeleteMapping(path = "/{orderId}")
  public void deletePointsTransaction (@PathVariable("orderId") Integer orderId) {

    orderFacade.deleteOrderBy(orderId);
  }


  @GetMapping(path = "/{orderId}")
  public ResponseEntity<OrderDTO> getOrderById (@PathVariable("orderId") Integer orderId) {

    return ResponseEntity.ok(orderFacade.getOrderBy(orderId));
  }


  @PutMapping(path = "/{orderId}")
  public ResponseEntity<OrderDTO> updatePointsTransaction (
      @PathVariable("orderId") Integer orderId,
      @RequestBody @Valid PointsTransactionDTO pointsTransactionDTO
  ) {

    return ResponseEntity.created(URI.create("/pointstransaction/"))
        .body(orderFacade.updateOrder(pointsTransactionDTO.getOrder()));
  }
}
