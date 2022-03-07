package com.rakuten.cep.points.service;


import com.rakuten.cep.points.domain.OrderItem;
import com.rakuten.cep.points.dto.OrderItemProjection;
import com.rakuten.cep.points.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class OrderItemService {

  @Autowired
  private OrderItemRepository orderItemRepository;


  public List<OrderItem> getAllBy (Integer orderId) {

    return orderItemRepository.getAllByOrderId(orderId);
  }


  public List<OrderItem> saveAll (List<OrderItem> orderItems) {

    return orderItemRepository.saveAll(orderItems);
  }


  public void deleteAllByOrderId (Integer orderId) {

    orderItemRepository.deleteAllByOrderId(orderId);
  }


  public List<OrderItemProjection> getOrderItemsBy (Integer orderId) {

    return orderItemRepository.getAllOrderItemsProjectionBy(orderId);
  }


  public List<OrderItemProjection> getOrderItemsBy (Set<Integer> orderItemIds) {

    return orderItemRepository.getAllOrderItemsProjectionByIds(orderItemIds);
  }


  public void deleteOrderItemsBy (Set<Integer> deletedOrderItemIds) {

    orderItemRepository.deleteAllBy(deletedOrderItemIds);
  }
}
