package com.otsnd.productmanager.dto;

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
    private Double price;
    private Integer stock;
}
