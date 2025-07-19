package com.otsnd.productmanager.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    public Double getTotalOrderPrice() {
        return this.getItems().stream()
                .filter(Objects::nonNull)
                .filter(orderItem -> orderItem.getQuantity() != null && orderItem.getProduct().getPrice() != null)
                .map(orderItem -> orderItem.getQuantity() * orderItem.getProduct().getPrice())
                .mapToDouble(Double::valueOf).sum();
    }
}
