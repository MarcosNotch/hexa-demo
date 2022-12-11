package com.example.demo.producto.domain;

import java.util.Optional;

public interface ProductoRepository {

    Long generateId();

    void save(Producto producto);

    Optional<Producto> findById(Long id);

    boolean existsProductoWithCodigo(String codigo);

    void delete(Producto producto);
}
