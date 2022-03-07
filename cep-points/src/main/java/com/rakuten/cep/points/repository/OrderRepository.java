package com.rakuten.cep.points.repository;


import com.rakuten.cep.points.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrderRepository extends JpaRepository<Order, Integer> {

  @Modifying
  @Query(value = "delete from cep_points.order where id = :orderId", nativeQuery = true)
  void deleteById (@Param("orderId") Integer orderId);
}
