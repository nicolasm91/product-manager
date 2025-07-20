package com.otsnd.productmanager.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otsnd.productmanager.dto.response.ProductDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderItemRequestDTO {
    @JsonProperty("product_id")
    @NotNull(message = "product_id cannot be empty")
    private Long productId;
    @JsonProperty("quantity")
    @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be at least 1")
    private Integer quantity;
    @Setter
    private ProductDTO product;

}


