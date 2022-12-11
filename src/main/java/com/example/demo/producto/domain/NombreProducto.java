package com.example.demo.producto.domain;

import com.example.demo.shared.domain.InvalidValueObjectException;
import com.example.demo.shared.domain.ValueObject;

public class NombreProducto extends ValueObject<String> {

    public NombreProducto(String value) {
        super(value);
    }

    @Override
    public void validate(String value) {
        if(value == null || value.length() > 200) {
            throw new InvalidValueObjectException("nombre producto no valido");
        }
    }
}
