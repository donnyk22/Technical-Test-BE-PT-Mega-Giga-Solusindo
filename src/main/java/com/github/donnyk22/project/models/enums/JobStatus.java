package com.github.donnyk22.project.models.enums;

public enum JobStatus {
    PENDING("PENDING"),
    RUNNING("RUNNING"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    private final String value;

    JobStatus(String value) {
        this.value = value;
    }
    
    public String val() {
        return value;
    }
}