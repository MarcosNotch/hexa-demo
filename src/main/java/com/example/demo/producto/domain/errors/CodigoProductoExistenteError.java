package com.example.demo.producto.domain.errors;

import com.example.demo.shared.domain.BusinessException;

public class CodigoProductoExistenteError extends BusinessException {

    public CodigoProductoExistenteError() {
        super("codigo producto existente");
    }
}
