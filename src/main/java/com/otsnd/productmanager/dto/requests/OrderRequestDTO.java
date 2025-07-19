package com.otsnd.productmanager.dto.requests;

import java.util.List;

public class OrderRequestDTO {
    private Long userId;
    private List<OrderItemRequestDTO> items;
}
