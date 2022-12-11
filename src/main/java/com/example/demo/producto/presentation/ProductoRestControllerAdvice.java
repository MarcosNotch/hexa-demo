package com.example.demo.producto.presentation;

import com.example.demo.shared.presentation.AbstractRestControllerAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackageClasses = ProductoController.class)
public class ProductoRestControllerAdvice extends AbstractRestControllerAdvice {

    private static final String SERVICE_PREFIX = "PRD";

    @Override
    public String servicePrefix() {
        return SERVICE_PREFIX;
    }
}
