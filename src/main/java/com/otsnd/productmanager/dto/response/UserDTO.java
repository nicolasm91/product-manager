package com.otsnd.productmanager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("user_id")
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private  String lastName;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("orders_quantity")
    private int ordersQuantity;
    @JsonProperty("orders_total_spent")
    private Double totalSpent;
}
