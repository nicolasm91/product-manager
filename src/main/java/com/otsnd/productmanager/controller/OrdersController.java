package com.otsnd.productmanager.controller;

import com.otsnd.productmanager.constants.Constants;
import com.otsnd.productmanager.dto.OrderDTO;
import com.otsnd.productmanager.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrdersController {
    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> fetchOrdersByUser(@PathVariable Long id) {
        List<OrderDTO> orders = this.orderService.findOrdersByUserId(id);

        return (orders.isEmpty()) ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(Constants.ERROR_MESSAGE, "No orders found for user with id " + id)) :
                ResponseEntity.ok(orders);
    }
}
