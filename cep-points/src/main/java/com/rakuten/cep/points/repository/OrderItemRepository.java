package com.rakuten.cep.points.repository;


import com.rakuten.cep.points.domain.OrderItem;
import com.rakuten.cep.points.dto.OrderItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;


public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

  @Query(value = "select * from cep_points.order_item where order_id = :orderId",
         nativeQuery = true)
  List<OrderItem> getAllByOrderId (@Param("orderId") Integer orderId);


  @Modifying
  @Query(value = "delete from cep_points.order_item where order_id = :orderId", nativeQuery = true)
  void deleteAllByOrderId (@Param("orderId") Integer orderId);


  @Query(value = "select price, quantity, rate from cep_points.order_item where order_id = :orderId",
         nativeQuery = true)
  List<OrderItemProjection> getAllOrderItemsProjectionBy (@Param("orderId") Integer orderId);


  @Query(value = "select price, quantity, rate from cep_points.order_item where id in :ids",
         nativeQuery = true)
  List<OrderItemProjection> getAllOrderItemsProjectionByIds (@Param("ids") Set<Integer> ids);


  @Modifying
  @Query(value = "delete from cep_points.order_item where id in :deletedOrderItemIds",
         nativeQuery = true)
  void deleteAllBy (@Param("deletedOrderItemIds") Set<Integer> deletedOrderItemIds);
}
