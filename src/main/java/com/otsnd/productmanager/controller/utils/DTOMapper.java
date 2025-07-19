package com.otsnd.productmanager.controller.utils;

import com.otsnd.productmanager.dto.OrderDTO;
import com.otsnd.productmanager.dto.OrderItemDTO;
import com.otsnd.productmanager.dto.ProductDTO;
import com.otsnd.productmanager.dto.UserDTO;
import com.otsnd.productmanager.entity.Order;
import com.otsnd.productmanager.entity.OrderItem;
import com.otsnd.productmanager.entity.Product;
import com.otsnd.productmanager.entity.User;


public class DTOMapper {
    public static OrderDTO mapOrderDTO(Order order) {
        return new OrderDTO(order.getId(),
                order.getItems()
                        .stream()
                        .map(DTOMapper::mapOrderItemDTO)
                        .toList(),
                order.getTotalOrderPrice());
    }

    public static OrderItemDTO mapOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO(orderItem.getQuantity(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getPartialItemPrice());
    }

    public static ProductDTO mapProductDTO(Product product) {
        return new ProductDTO(product.getId(),
                product.getName(),
                (Math.round(product.getPrice() * 100.0) / 100.0),
                product.getStock());
    }

    public static UserDTO mapUserDTO(User user) {
        return new UserDTO(user.getId(),
                user.getName(),
                user.getLastName(),
                user.getUsername(),
                user.getOrders().size(),
                user.getOrdersTotalPrice());
    }
}
