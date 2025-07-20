package com.otsnd.productmanager.controller;

import com.otsnd.productmanager.dto.requests.OrderRequestDTO;
import com.otsnd.productmanager.dto.response.OrderDTO;
import com.otsnd.productmanager.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static com.otsnd.productmanager.constants.Constants.ERROR_MESSAGE;

@RestController
@RequestMapping("api/orders")
public class OrdersController {
    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewOrder(@Valid @RequestBody OrderRequestDTO request) {
        OrderDTO order = this.orderService.placeOrder(request);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> fetchOrdersByUser(@PathVariable Long id) {
        List<OrderDTO> orders = this.orderService.findOrdersByUserId(id);

        return (orders.isEmpty()) ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(ERROR_MESSAGE, "No orders found for user with id " + id)) :
                ResponseEntity.ok(orders);
    }
}
