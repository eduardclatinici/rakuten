package com.rakuten.cep.points.facade;


import com.rakuten.cep.points.domain.Customer;
import com.rakuten.cep.points.domain.Order;
import com.rakuten.cep.points.domain.OrderItem;
import com.rakuten.cep.points.domain.Product;
import com.rakuten.cep.points.dto.CampaignProductDTO;
import com.rakuten.cep.points.dto.OrderDTO;
import com.rakuten.cep.points.dto.OrderItemDTO;
import com.rakuten.cep.points.dto.ProductDTO;
import com.rakuten.cep.points.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import static java.util.stream.Collectors.toList;


@Service
@Transactional
public class OrderFacade {

  @Autowired
  private OrderService orderService;


  public OrderDTO saveOrder (OrderDTO orderDTO) {

    Customer customer = orderService.getCustomerByEmail(orderDTO.getCustomerEmail());
    Order order = orderService.saveOrder(transformOrderFrom(customer));
    Set<CampaignProductDTO> existingProducts = orderService.getExistingProductsDTO(
        getOrderProductIdsFrom(orderDTO));
    List<OrderItem> orderItems = transformOrderItemsFrom(
        orderDTO.getItems(),
        existingProducts,
        order
    );
    orderService.saveOrderItemsFor(customer, orderItems);
    return transformOrderDTOFrom(order, orderItems);
  }


  private Set<Integer> getOrderProductIdsFrom (OrderDTO orderDTO) {

    return orderDTO.getItems()
        .stream()
        .map(orderProduct -> orderProduct.getProduct().getId())
        .collect(Collectors.toSet());
  }


  private OrderDTO transformOrderDTOFrom (Order order, List<OrderItem> orderItems) {

    return new OrderDTO(
        order.getId(),
        order.getCustomer().getEmail(),
        order.getCreatedAt(),
        transformOrderItemDTOFrom(orderItems)
    );
  }


  private List<OrderItemDTO> transformOrderItemDTOFrom (List<OrderItem> orderItems) {

    return orderItems.stream()
        .map(orderItem -> new OrderItemDTO(
            orderItem.getId(),
            transformProductDTOFrom(orderItem.getProduct()),
            orderItem.getQuantity()
        ))
        .collect(toList());
  }


  private ProductDTO transformProductDTOFrom (Product product) {

    return ProductDTO.builder()
        .id(product.getProductId())
        .name(product.getName())
        .price(product.getPrice())
        .build();
  }


  private Order transformOrderFrom (Customer customer) {

    return Order.builder()
        .createdAt(LocalDateTime.now())
        .customer(customer)
        .build();
  }


  private List<OrderItem> transformOrderItemsFrom (
      List<OrderItemDTO> orderItemsDTOs,
      Set<CampaignProductDTO> productsDTO,
      Order order
  ) {

    Map<Integer, Product> productsMap = mapProductsToIdsFrom(productsDTO);
    Map<Integer, BigDecimal> rateMap = mapRateToIdsFrom(productsDTO);
    return orderItemsDTOs.stream()
        .map(orderItemDTO -> OrderItem.builder()
            .product(productsMap.get(orderItemDTO.getProduct().getId()))
            .quantity(orderItemDTO.getQuantity())
            .order(order)
            .rate(rateMap.get(orderItemDTO.getProduct().getId()))
            .build())
        .collect(toList());
  }


  private Map<Integer, BigDecimal> mapRateToIdsFrom (Set<CampaignProductDTO> productsDTO) {

    return productsDTO.stream()
        .collect(Collectors.toMap(CampaignProductDTO::getId, CampaignProductDTO::getRate));
  }


  private Map<Integer, Product> mapProductsToIdsFrom (Set<CampaignProductDTO> productsDTO) {

    return productsDTO.stream()
        .map(campaignProductDTO -> Product.builder()
            .productId(campaignProductDTO.getId())
            .name(campaignProductDTO.getName())
            .price(campaignProductDTO.getPrice())
            .build())
        .collect(Collectors.toMap(Product::getProductId, Function.identity()));
  }


  public void deleteOrderBy (Integer orderId) {

    orderService.deleteOrderBy(orderId);
  }


  public OrderDTO getOrderBy (Integer orderId) {

    Order order = orderService.getOrderBy(orderId);
    return transformOrderDTOFrom(order, order.getOrderItems());
  }


  public OrderDTO updateOrder (OrderDTO orderDTO) {

    Order order = orderService.getOrderBy(orderDTO.getId());
    if (!orderDTO.getCustomerEmail().equals(order.getCustomer().getEmail())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot change customer for order");
    }
    Set<CampaignProductDTO> existingProducts = orderService.getExistingProductsDTO(
        getOrderProductIdsFrom(orderDTO));
    List<OrderItem> newOrderItems = transformOrderItemsFrom(
        getNewOrderItemsDTO(orderDTO),
        existingProducts,
        order
    );
    orderService.updateOrderItems(orderDTO, order, newOrderItems);
    return transformOrderDTOFrom(order, orderService.getAllOrderItemsBy(order.getId()));
  }


  private List<OrderItemDTO> getNewOrderItemsDTO (OrderDTO orderDTO) {

    return orderDTO.getItems().stream()
        .filter(orderItemDTO -> orderItemDTO.getId() == null)
        .collect(toList());
  }
}
