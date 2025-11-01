package com.github.donnyk22.project.models.enums;

public enum OrderStatus {
    PENDING("PENDING"),
    PAID("PAID"),
    CANCELLED("CANCELED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}