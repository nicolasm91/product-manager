package com.otsnd.productmanager.controller;

import com.otsnd.productmanager.dto.OrderDTO;
import com.otsnd.productmanager.dto.OrderItemDTO;
import com.otsnd.productmanager.dto.ProductDTO;
import com.otsnd.productmanager.dto.UserDTO;
import com.otsnd.productmanager.entity.Order;
import com.otsnd.productmanager.entity.OrderItem;
import com.otsnd.productmanager.entity.Product;
import com.otsnd.productmanager.entity.User;
import com.otsnd.productmanager.exceptions.OrderItemInvalidException;
import com.otsnd.productmanager.exceptions.ProductMissingException;
import com.otsnd.productmanager.service.OrderService;
import com.otsnd.productmanager.service.ProductsService;
import com.otsnd.productmanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api")
public class UserController {
    private final OrderService orderService;
    private final UserService userService;
    private final ProductsService productsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final String ERROR_MESSAGE = "error_message";

    public UserController(OrderService orderService,
                          UserService userService,
                          ProductsService productsService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productsService = productsService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id) {
        Optional<Product> productOpt = this.productsService.findById(id);

        if (productOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(ERROR_MESSAGE, "product with id " + id + " not found"));

        Product product = productOpt.get();

        return ResponseEntity.ok(new ProductDTO(product.getId(),
                product.getName(),
                (Math.round(product.getPrice() * 100.0) / 100.0),
                product.getStock()));

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        Optional<User> userOpt = this.userService.findById(id);

        if (userOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(ERROR_MESSAGE, "user with id " + id + " not found"));

        User user = userOpt.get();

        return ResponseEntity
                .ok(new UserDTO(user.getId(),
                        user.getName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getOrders().size(),
                        user.getOrdersTotalPrice()));
    }

    @GetMapping("/user/{id}/orders")
    public ResponseEntity<?> fetchOrdersByUser(@PathVariable Long id) {
        List<Order> orders = this.orderService.findOrdersByUserId(id);

        if (orders.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(ERROR_MESSAGE, "No orders found for user with id " + id));

        List<OrderDTO> body = orders.stream().map(UserController::mapOrderDTO).toList();

        return ResponseEntity.ok(body);
    }

    private static OrderDTO mapOrderDTO(Order order) {
        List<OrderItemDTO> orderItems = order.getItems()
                .stream()
                .map(UserController::mapOrderItemDTO)
                .toList();

        return new OrderDTO(order.getId(), orderItems, order.getTotalOrderPrice());
    }

    private static OrderItemDTO mapOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO(orderItem.getQuantity(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getPartialItemPrice());
    }
}
