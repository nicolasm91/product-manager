package com.otsnd.productmanager.controller;

import com.otsnd.productmanager.entity.Product;
import com.otsnd.productmanager.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductsController {
    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/")
    public ResponseEntity<?> fetchProducts() {
        List<Product> products = this.productsService.findAll();

        return ResponseEntity.ok(products);
    }
}
