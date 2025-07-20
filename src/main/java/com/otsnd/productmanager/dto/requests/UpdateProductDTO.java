package com.otsnd.productmanager.dto.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UpdateProductDTO {
    @JsonIgnore
    private Long productId;
    @Nullable
    private String name;
    @Nullable
    private String description;
    @Min(value = 1, message = "price must be at least 1")
    private Double price;
    @Min(value = 1, message = "stock must be at least 1")
    private Integer stock;

    public boolean isInvalid() {
        boolean invalidName = name != null && (name.isEmpty() || name.isBlank());
        boolean invalidDescription = description != null && (description.isEmpty() || description.isBlank() || description.length() > 500);

        return invalidName && invalidDescription && productId != null && price == null && stock == null;
    }
}
