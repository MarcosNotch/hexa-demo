package com.example.demo.producto.infra;

import com.example.demo.producto.domain.CodigoProducto;
import com.example.demo.producto.domain.NombreProducto;
import com.example.demo.producto.domain.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoEntity toEntity(Producto domain) {
        return ProductoEntity.builder()
                .uuid(domain.getUuid())
                .id(domain.getId())
                .codigo(domain.getCodigo().getValue())
                .nombre(domain.getNombre().getValue())
                .build();
    }

    public Producto toDomain(ProductoEntity entity) {
        return Producto.builder()
                .uuid(entity.getUuid())
                .id(entity.getId())
                .codigo(new CodigoProducto(entity.getCodigo()))
                .nombre(new NombreProducto(entity.getNombre()))
                .build();
    }

}
