package com.noa.pos.imp.constant;

public enum OrderState {
    PROCESSED("PROCESSED"),
    PREPARING("PREPARING"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED"),
    ;

    private final String name;

    private OrderState(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
