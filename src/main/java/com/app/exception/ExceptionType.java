package com.app.exception;

public enum ExceptionType {
    ENTITY_NOT_FOUND("not found"),
    ENTITY_ALREADY_EXISTS("already exists");

    String value;

    ExceptionType(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
