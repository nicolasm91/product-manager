package com.otsnd.productmanager.service;

import com.otsnd.productmanager.controller.utils.DTOMapper;
import com.otsnd.productmanager.dto.requests.CreateProductDTO;
import com.otsnd.productmanager.dto.requests.UpdateProductDTO;
import com.otsnd.productmanager.dto.response.ProductDTO;
import com.otsnd.productmanager.entity.Product;
import com.otsnd.productmanager.exceptions.DuplicatedProductName;
import com.otsnd.productmanager.exceptions.ProductMissingException;
import com.otsnd.productmanager.exceptions.ProductUpdateInvalidException;
import com.otsnd.productmanager.repository.ProductRepository;
import org.apache.logging.log4j.util.Strings;
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

    public Product newProduct(CreateProductDTO productDTO) {
        boolean nameMatches = repository.findAll()
                .stream()
                .anyMatch(product -> product.getName().equals(productDTO.getName()));

        if (nameMatches) throw new DuplicatedProductName();

        return repository.save(Product.builder()
                .name(productDTO.getName())
                .stock(productDTO.getStock())
                .price(productDTO.getPrice())
                .build());
    }

    public Product updateExistingProduct(UpdateProductDTO productDTO) {
        if (productDTO.isInvalid()) throw new ProductUpdateInvalidException();

        Optional<Product> match = repository.findAll()
                .stream()
                .filter(product -> product.getId().equals(productDTO.getProductId()))
                .findAny();

        if (match.isEmpty()) throw new ProductMissingException();

        Product product = match.get();

        // Updates:
        boolean changed = false;
        if (Strings.isNotBlank(productDTO.getName())) {
            product.setName(productDTO.getName());
            changed = true;
        }

        if (Strings.isNotBlank(productDTO.getDescription()) && productDTO.getDescription().length() <= 500) {
            product.setDescription(productDTO.getDescription());
            changed = true;
        }

        if (productDTO.getStock() != null && productDTO.getStock() > 0) {
            product.setStock(productDTO.getStock()); // only allows increase stock
            changed = true;
        }

        if (productDTO.getPrice() != null && productDTO.getPrice() > 0) {
            product.setPrice(productDTO.getPrice()); // doesn't allow free products
            changed = true;
        }

        if (!changed) throw new ProductUpdateInvalidException();

        return repository.save(product);
    }
}
