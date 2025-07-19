package com.otsnd.productmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "APP_USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Order> orders;


    public Double getOrdersTotalPrice() {
        double total = this.getOrders().stream()
                .map(Order::getTotalOrderPrice).mapToDouble(Double::valueOf).sum();

        return Math.round(total * 100.0) / 100.0;
    }
}
