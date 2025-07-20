package com.otsnd.productmanager.exceptions;

public class RepeatedProductInRequestException extends RuntimeException {
    public RepeatedProductInRequestException() {
        super("Orders can't contain repeated products");
    }
}
