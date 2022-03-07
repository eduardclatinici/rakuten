package com.rakuten.cep.points.service;


import com.rakuten.cep.points.domain.Customer;
import com.rakuten.cep.points.domain.Order;
import com.rakuten.cep.points.domain.OrderItem;
import com.rakuten.cep.points.dto.CampaignProductDTO;
import com.rakuten.cep.points.dto.OrderDTO;
import com.rakuten.cep.points.dto.OrderItemDTO;
import com.rakuten.cep.points.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import static java.util.stream.Collectors.toSet;


@Service
@Transactional
public class OrderService {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private OrderItemService orderItemService;

  @Autowired
  private OrderRepository orderRepository;

  @Value("${cep.campaign.url}")
  private String campaignUrl;

  private static CampaignService campaignService;


  public Customer getCustomerByEmail (String email) {

    return customerService.getCustomerByEmail(email);
  }


  public Set<CampaignProductDTO> getExistingProductsDTO (Set<Integer> orderProductIds) {

    campaignService = CampaignService.getInstance(campaignUrl);
    Set<CampaignProductDTO> existingproductsDTO = campaignService.getExistingProductsMatching(
        orderProductIds);
    Set<Integer> invalidProductIds = getInvalidProductIds(
        orderProductIds,
        getProductIdsFrom(existingproductsDTO)
    );
    if (!invalidProductIds.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Products with ids:" + invalidProductIds.toString() + " were not found"
      );
    }
    return existingproductsDTO;
  }


  private Set<Integer> getProductIdsFrom (Set<CampaignProductDTO> productsDTO) {

    return productsDTO.stream().map(CampaignProductDTO::getId).collect(Collectors.toSet());
  }


  private Set<Integer> getInvalidProductIds (
      Set<Integer> orderProductIds,
      Set<Integer> existingProductIds
  ) {

    return orderProductIds.stream()
        .filter(id -> !existingProductIds.contains(id))
        .collect(Collectors.toSet());
  }


  public Order saveOrder (Order order) {

    return orderRepository.save(order);
  }


  public void saveOrderItemsFor (Customer customer, List<OrderItem> orderItems) {

    saveAllOrderItems(orderItems);
    BigDecimal pointsForOrder = orderItems.stream()
        .map(orderItem -> new BigDecimal(orderItem.getQuantity()).multiply(orderItem.getRate()
                                                                               .multiply(orderItem.getProduct()
                                                                                             .getPrice())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    customer.setPointsBalance(customer.getPointsBalance().add(pointsForOrder));
    customerService.update(customer);
  }


  private List<OrderItem> saveAllOrderItems (List<OrderItem> orderItems) {

    return orderItemService.saveAll(orderItems);
  }


  public void deleteOrderBy (Integer orderId) {

    customerService.updatePointsBalanceBy(orderId, orderItemService.getOrderItemsBy(orderId));
    orderItemService.deleteAllByOrderId(orderId);
    orderRepository.deleteById(orderId);
  }


  public Order getOrderBy (Integer orderId) {

    return orderRepository.findById(orderId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Order not found for id: " + orderId
        ));
  }


  public void updateOrderItems (OrderDTO orderDTO, Order order, List<OrderItem> newOrderItems) {

    if (!newOrderItems.isEmpty()) {
      saveOrderItemsFor(order.getCustomer(), newOrderItems);
    }
    Set<Integer> deletedOrderItemIds = getDeletedOrderItemIdsFrom(
        order.getOrderItems(),
        orderDTO.getItems()
    );
    if (!deletedOrderItemIds.isEmpty()) {
      deleteOrderItemsFor(order.getId(), deletedOrderItemIds);
    }
  }


  public void deleteOrderItemsFor (Integer orderId, Set<Integer> deletedOrderItemIds) {

    customerService.updatePointsBalanceForDeletedItems(
        orderId,
        orderItemService.getOrderItemsBy(deletedOrderItemIds)
    );
    orderItemService.deleteOrderItemsBy(deletedOrderItemIds);
  }


  private Set<Integer> getDeletedOrderItemIdsFrom (
      List<OrderItem> savedItems,
      List<OrderItemDTO> itemsDTO
  ) {

    Set<Integer> itemIds = itemsDTO.stream().map(OrderItemDTO::getId).collect(toSet());
    return savedItems.stream()
        .map(OrderItem::getId)
        .filter(savedItemId -> !itemIds.contains(savedItemId))
        .collect(toSet());
  }


  public List<OrderItem> getAllOrderItemsBy (Integer orderId) {

    return orderItemService.getAllBy(orderId);
  }
}
