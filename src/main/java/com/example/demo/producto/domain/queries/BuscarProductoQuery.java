package com.example.demo.producto.domain.queries;

import com.example.demo.producto.domain.Producto;
import com.example.demo.shared.domain.Query;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BuscarProductoQuery implements Query<Producto> {

    private final Long id;
}
