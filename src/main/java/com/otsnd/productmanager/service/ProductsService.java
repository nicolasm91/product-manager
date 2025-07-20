package com.otsnd.productmanager.service;

import com.otsnd.productmanager.controller.utils.DTOMapper;
import com.otsnd.productmanager.dto.response.ProductDTO;
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

    public Optional<ProductDTO> findById(long id) {
        return repository.findById(id)
                .map(DTOMapper::mapProductDTO);
    }

    public List<ProductDTO> findAll() {
        return this.repository.findAll()
                .stream()
                .map(DTOMapper::mapProductDTO)
                .toList();
    }

    public List<Product> findAllEntities() {
        return repository.findAll();
    }
    public Product save(Product product) {
        return repository.save(product);
    }
}
