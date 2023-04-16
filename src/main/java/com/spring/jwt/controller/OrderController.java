package com.spring.jwt.controller;


import com.spring.jwt.dto.CustomResponse;
import com.spring.jwt.dto.OrderFormRequest;
import com.spring.jwt.entity.Order;
import com.spring.jwt.entity.OrderDetail;
import com.spring.jwt.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Order>>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        CustomResponse response = new CustomResponse<>(HttpStatus.OK.value(), "success", orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<CustomResponse<List<OrderDetail>>> getOrderDetails(@PathVariable Long id) {
        List<OrderDetail> orderDetails = orderService.getOrderDetails(id);
        CustomResponse response = new CustomResponse<>(HttpStatus.OK.value(), "success", orderDetails);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Order>> addOrder(@RequestBody OrderFormRequest request) {
        Order order = orderService.addOrder(request);
        CustomResponse response = new CustomResponse<>(HttpStatus.OK.value(), "success", order);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> addOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        CustomResponse response = new CustomResponse<>(HttpStatus.OK.value(), "success", null);
        return ResponseEntity.ok(response);
    }
}
