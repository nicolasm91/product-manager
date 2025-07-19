package com.otsnd.productmanager.service;

import com.otsnd.productmanager.entity.Product;
import com.otsnd.productmanager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    private final ProductRepository repository;

    public ProductsService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product findById(long id) {
        return repository.findById(id);
    }

    public List<Product> findAll() {
        return this.repository.findAll();
    }
}
