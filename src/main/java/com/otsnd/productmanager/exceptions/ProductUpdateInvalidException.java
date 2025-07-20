package com.otsnd.productmanager.exceptions;

public class ProductUpdateInvalidException extends RuntimeException {
    public ProductUpdateInvalidException() {
        super("input parameters are not valid for updating an existing product");
    }
}
