package com.example.demo.stock.domain.errors;

import com.example.demo.shared.domain.BusinessException;

public class StockInexistenteError extends BusinessException {

    public StockInexistenteError() {
        super("stock inexistente");
    }

}
