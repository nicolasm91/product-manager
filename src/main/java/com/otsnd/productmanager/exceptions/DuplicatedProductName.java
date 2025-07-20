package com.otsnd.productmanager.exceptions;

public class DuplicatedProductName extends RuntimeException {
    public DuplicatedProductName() {
        super("Product name already exists");
    }
}
