package com.spring.jwt.service.order;

import com.spring.jwt.dto.OrderFormRequest;
import com.spring.jwt.entity.Order;
import com.spring.jwt.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getAllOrders();

    Optional<Order> getOrder(Long id);

    Order addOrder(OrderFormRequest request);

    void deleteOrder(Long id);


    List<OrderDetail> getOrderDetails(Long orderId);

}
