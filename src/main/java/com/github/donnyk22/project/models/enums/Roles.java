package com.github.donnyk22.project.models.enums;

public enum Roles {
    ADMIN("admin"),
    USER("user");

    private final String value;

    Roles(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
