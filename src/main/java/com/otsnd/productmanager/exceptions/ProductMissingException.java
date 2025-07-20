package com.otsnd.productmanager.exceptions;

public class ProductMissingException extends RuntimeException {
    public ProductMissingException() {
        super("product not found");
    }
}
