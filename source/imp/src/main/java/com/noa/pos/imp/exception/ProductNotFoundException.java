package com.noa.pos.imp.exception;

public class ProductNotFoundException extends Exception {
    private final String code;
    private final String description;

    public ProductNotFoundException(String code, String description) {
        super(description);
        this.code = code;
        this.description = description;
    }
}
