package com.otsnd.productmanager.entity;

import com.otsnd.productmanager.exceptions.OrderItemInvalidException;
import com.otsnd.productmanager.exceptions.ProductMissingException;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ORDER_ITEM")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Double getPartialItemPrice() throws ProductMissingException, OrderItemInvalidException {
        if (this.getProduct() == null || this.getProduct().getPrice() == null) throw new ProductMissingException("order item id" +  this.getId() + " has null or invalid product");

        if (this.getQuantity() == null) throw new OrderItemInvalidException("order item id" +  this.getId() + " has null quantity");

        return  this.getProduct().getPrice() * this.getQuantity();
    }
}
