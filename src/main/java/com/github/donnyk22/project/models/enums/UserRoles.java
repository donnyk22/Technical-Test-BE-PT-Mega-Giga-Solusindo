package com.github.donnyk22.project.models.enums;

public enum UserRoles {
    ADMIN("admin"),
    USER("user");

    private final String value;

    UserRoles(String value) {
        this.value = value;
    }
    
    public String val() {
        return value;
    }
}
