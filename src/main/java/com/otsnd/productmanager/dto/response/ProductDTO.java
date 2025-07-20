package com.otsnd.productmanager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    @JsonProperty("product_id")
    private Long id;
    private  String name;
    private String description;
    private Double price;
    private Integer stock;
}
