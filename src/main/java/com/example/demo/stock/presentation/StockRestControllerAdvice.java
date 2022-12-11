package com.example.demo.stock.presentation;

import com.example.demo.shared.presentation.AbstractRestControllerAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackageClasses = StockController.class)
public class StockRestControllerAdvice extends AbstractRestControllerAdvice {

    private static final String SERVICE_PREFIX = "STC";

    @Override
    public String servicePrefix() {
        return SERVICE_PREFIX;
    }

}
