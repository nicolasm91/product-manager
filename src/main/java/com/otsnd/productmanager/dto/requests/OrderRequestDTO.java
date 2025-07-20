package com.otsnd.productmanager.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDTO {
    @JsonProperty("user_id")
    @NotNull(message = "user_id cannot be empty")
    @Positive
    private Long userId;
    @JsonProperty("items")
    @NotEmpty(message = "items list cannot be empty")
    @Valid
    private List<OrderItemRequestDTO> items;
}
