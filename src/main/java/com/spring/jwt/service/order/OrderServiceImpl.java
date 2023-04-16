package com.spring.jwt.service.order;


import com.spring.jwt.dto.OrderFormRequest;
import com.spring.jwt.entity.Order;
import com.spring.jwt.entity.OrderDetail;
import com.spring.jwt.entity.User;
import com.spring.jwt.repository.OrderDetailRepository;
import com.spring.jwt.repository.OrderRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order addOrder(OrderFormRequest request) {
        Order order = new Order();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        order.setUser(user);
        order.setAddress(request.getAddress());
        order.setPhone(request.getPhone());
        order.setNote(request.getNote());
        Order newOrder = orderRepository.save(order);
        request.getProducts().forEach(product -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(getOrder(newOrder.getId()).orElseThrow());
            orderDetail.setProduct(productService.getProduct(product.getProductId()).orElseThrow());
            orderDetail.setQuantity(product.getQuantity());
            OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
        });
        return newOrder;
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = getOrder(id).orElseThrow();
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDetail> getOrderDetails(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
