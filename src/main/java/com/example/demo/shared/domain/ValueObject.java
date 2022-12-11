package com.example.demo.shared.domain;

public abstract class ValueObject<T> {

    private final T value;

    public ValueObject(T value) {
        validate(value);
        this.value = value;
    }

    public void validate(T value) throws InvalidValueObjectException {

    }

    public T getValue() {
        return value;
    }

}
