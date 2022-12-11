package com.example.demo.producto.application;

import com.example.demo.producto.domain.queries.BuscarProductoQuery;
import com.example.demo.producto.domain.Producto;
import com.example.demo.producto.domain.errors.ProductoInexistenteError;
import com.example.demo.producto.domain.ProductoRepository;
import com.example.demo.shared.application.Inject;
import com.example.demo.shared.application.QueryHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Inject
public class BuscarProducto implements QueryHandler<Producto, BuscarProductoQuery> {

    private final ProductoRepository productoRepository;

    @Override
    public Producto handle(BuscarProductoQuery query) {
        return productoRepository.findById(query.getId())
                .orElseThrow(ProductoInexistenteError::new);
    }
}
