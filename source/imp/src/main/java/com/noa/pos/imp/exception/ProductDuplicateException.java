package com.noa.pos.imp.exception;

public class ProductDuplicateException extends Exception {
    private final String code;
    private final String description;

    public ProductDuplicateException(String code, String description) {
        super(description);
        this.code = code;
        this.description = description;
    }
}
