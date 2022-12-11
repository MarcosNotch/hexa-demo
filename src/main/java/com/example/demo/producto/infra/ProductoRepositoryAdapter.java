package com.example.demo.producto.infra;

import com.example.demo.producto.domain.Producto;
import com.example.demo.producto.domain.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ProductoRepositoryAdapter implements ProductoRepository {

    private final ProductoRepositoryJPA productoRepositoryJPA;
    private final ProductoMapper mapper;

    @Override
    public Long generateId() {
        return productoRepositoryJPA.generateId();
    }

    @Override
    public void save(Producto producto) {
        productoRepositoryJPA.save(mapper.toEntity(producto));
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return productoRepositoryJPA.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsProductoWithCodigo(String codigo) {
        return productoRepositoryJPA.existsByCodigo(codigo);
    }

    @Override
    public void delete(Producto producto) {
        productoRepositoryJPA.delete(mapper.toEntity(producto));
    }
}
