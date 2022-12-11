package com.example.demo.producto.presentation;

import com.example.demo.producto.domain.Producto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductoDTO {

    private final Long id;
    private final String codigo;
    private final String nombre;

    public static ProductoDTO fromDomain(Producto domain) {
        return ProductoDTO.builder()
                .id(domain.getId())
                .codigo(domain.getCodigo().getValue())
                .nombre(domain.getNombre().getValue())
                .build();
    }
}
