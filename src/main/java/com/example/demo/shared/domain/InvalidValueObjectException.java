package com.example.demo.shared.domain;

public class InvalidValueObjectException extends RuntimeException {

    public InvalidValueObjectException(String message) {
        super(message);
    }
}
