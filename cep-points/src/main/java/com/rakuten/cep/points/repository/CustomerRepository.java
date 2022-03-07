package com.rakuten.cep.points.repository;


import com.rakuten.cep.points.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

  Optional<Customer> findByEmail (String email);


  @Modifying
  @Query(value = "update cep_points.customer "
      + "set points_balance = points_balance - :points "
      + "where id = (select customer_id from cep_points.order where id = :orderId)",
         nativeQuery = true)
  void updatePointsBalanceBy (
      @Param("orderId") Integer orderId,
      @Param("points") BigDecimal points
  );
}
