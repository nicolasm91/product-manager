package com.otsnd.productmanager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemDTO {
    private int quantity;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("item_price")
    private Double totalPrice;
}
