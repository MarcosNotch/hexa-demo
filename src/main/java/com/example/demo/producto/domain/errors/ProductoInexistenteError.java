package com.example.demo.producto.domain.errors;

import com.example.demo.shared.domain.BusinessException;

public class ProductoInexistenteError extends BusinessException {

    public ProductoInexistenteError() {
        super("producto inexistente");
    }
}
