package com.otsnd.productmanager.service;

import com.otsnd.productmanager.entity.Order;
import com.otsnd.productmanager.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> findOrdersByUserId(long id) {
        return repository.findAllByUserId(id);
    }
}
