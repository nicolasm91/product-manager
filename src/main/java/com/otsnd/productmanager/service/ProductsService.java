package com.otsnd.productmanager.service;

import com.otsnd.productmanager.entity.Product;
import com.otsnd.productmanager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    private final ProductRepository repository;

    public ProductsService(ProductRepository repository) {
        this.repository = repository;
    }

    public Optional<Product> findById(long id) {
        return repository.findById(id);
    }

    public List<Product> findAll() {
        return this.repository.findAll();
    }
}
