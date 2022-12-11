package com.example.demo.producto.domain;

import com.example.demo.shared.domain.InvalidValueObjectException;
import com.example.demo.shared.domain.ValueObject;

public class CodigoProducto extends ValueObject<String> {

    public CodigoProducto(String value) {
        super(value);
    }

    @Override
    public void validate(String value) {
        if(value == null || value.length() > 50) {
            throw new InvalidValueObjectException("codigo producto no valido");
        }
    }
}
