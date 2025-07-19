package com.otsnd.productmanager.controller;

import com.otsnd.productmanager.constants.Constants;
import com.otsnd.productmanager.dto.response.ProductDTO;
import com.otsnd.productmanager.service.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/products")
public class ProductsController {
    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id) {
        Optional<ProductDTO> product = this.productsService.findById(id);

        return (product.isPresent() ? ResponseEntity.ok().body(product.get()) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(Constants.ERROR_MESSAGE, "product with id " + id + " not found")));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> findAllProducts() {
        return ResponseEntity.ok(this.productsService.findAll()
                .stream()
                .toList());
    }
}


