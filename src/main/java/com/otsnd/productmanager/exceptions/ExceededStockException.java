package com.otsnd.productmanager.exceptions;

import com.otsnd.productmanager.dto.requests.OrderItemRequestDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class ExceededStockException extends RuntimeException {
    private final List<OrderItemRequestDTO> exceedStock;

    public ExceededStockException(String message, List<OrderItemRequestDTO> exceedStock) {
        super(message);
        this.exceedStock = exceedStock;
    }
}
