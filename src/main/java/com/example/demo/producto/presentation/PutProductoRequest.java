package com.example.demo.producto.presentation;

import lombok.Data;

@Data
public class PutProductoRequest {

    private final String codigo;
    private final String nombre;

}
