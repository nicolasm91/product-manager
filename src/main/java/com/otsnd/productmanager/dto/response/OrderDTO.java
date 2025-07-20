package com.otsnd.productmanager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    @JsonProperty("order_id")
    private Long id;
    private List<OrderItemDTO> items;
    @JsonProperty("total_prize")
    private Double totalPrice;

    public OrderDTO(long id, List<OrderItemDTO> items, Double totalPrice) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
