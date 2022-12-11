package com.example.demo.producto.domain;

import com.example.demo.producto.domain.commands.CrearProductoCommand;
import com.example.demo.producto.domain.commands.ModificarProductoCommand;
import com.example.demo.producto.domain.events.ProductoCreadoEvent;
import com.example.demo.producto.domain.events.ProductoEliminadoEvent;
import com.example.demo.producto.domain.events.ProductoModificadoEvent;
import com.example.demo.shared.domain.Aggregate;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class Producto extends Aggregate {

    private final UUID uuid;
    private final Long id;
    private final CodigoProducto codigo;
    private final NombreProducto nombre;

    public static Producto create(Long id, CrearProductoCommand command) {
        Producto producto = Producto.builder()
                .uuid(UUID.randomUUID())
                .id(id)
                .codigo(new CodigoProducto(command.getCodigo()))
                .nombre(new NombreProducto(command.getNombre()))
                .build();

        producto.push(new ProductoCreadoEvent(producto));

        return producto;
    }

    public Producto update(ModificarProductoCommand command) {
        Producto producto = Producto.builder()
                .uuid(this.uuid)
                .id(this.id)
                .codigo(new CodigoProducto(command.getCodigo()))
                .nombre(new NombreProducto(command.getNombre()))
                .build();

        producto.push(new ProductoModificadoEvent(producto));

        return producto;
    }

    public Producto eliminar() {
        this.push(new ProductoEliminadoEvent(this));
        return this;
    }

}
