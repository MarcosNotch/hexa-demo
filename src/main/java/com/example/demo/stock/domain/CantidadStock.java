package com.example.demo.stock.domain;

import com.example.demo.shared.domain.InvalidValueObjectException;
import com.example.demo.shared.domain.ValueObject;

public class CantidadStock extends ValueObject<Long> {

    public CantidadStock(Long value) {
        super(value);
    }

    @Override
    public void validate(Long value) {
        if(value == null || value < 0) {
            throw new InvalidValueObjectException("cantidad stock no valido");
        }
    }
}
