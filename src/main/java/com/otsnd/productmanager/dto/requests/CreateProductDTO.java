package com.otsnd.productmanager.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProductDTO {
    @NotEmpty
    @Valid
    private String name;
    @NotNull(message = "price cannot be null")
    @Min(value = 1, message = "price must be at least 1")
    @Valid
    private Double price;
    @NotNull(message = "stock cannot be null")
    @Min(value = 1, message = "stock must be at least 1")
    @Valid
    private Integer stock;
}
