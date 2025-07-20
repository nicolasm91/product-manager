package com.otsnd.productmanager.controller.utils;

import com.otsnd.productmanager.dto.response.OrderDTO;
import com.otsnd.productmanager.dto.response.OrderItemDTO;
import com.otsnd.productmanager.dto.response.ProductDTO;
import com.otsnd.productmanager.dto.response.UserDTO;
import com.otsnd.productmanager.entity.Order;
import com.otsnd.productmanager.entity.OrderItem;
import com.otsnd.productmanager.entity.Product;
import com.otsnd.productmanager.entity.User;


public class DTOMapper {
    public static Double roundValues(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static OrderDTO mapOrderDTO(Order order) {
        return new OrderDTO(order.getId(),
                order.getItems()
                        .stream()
                        .map(DTOMapper::mapOrderItemDTO)
                        .toList(),
                roundValues(order.getTotalOrderPrice()),
                order.getCreatedAt());
    }

    public static OrderItemDTO mapOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO(orderItem.getQuantity(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                roundValues(orderItem.getPartialItemPrice()));
    }

    public static ProductDTO mapProductDTO(Product product) {
        return new ProductDTO(product.getId(),
                product.getName(),
                product.getDescription(),
                roundValues(product.getPrice()),
                product.getStock());
    }

    public static UserDTO mapUserDTO(User user) {
        return new UserDTO(user.getId(),
                user.getName(),
                user.getLastName(),
                user.getUsername(),
                user.getOrders().size(),
                roundValues(user.getOrdersTotalPrice()));
    }
}
