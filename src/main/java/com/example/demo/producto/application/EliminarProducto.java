package com.example.demo.producto.application;

import com.example.demo.producto.domain.commands.EliminarProductoCommand;
import com.example.demo.producto.domain.Producto;
import com.example.demo.producto.domain.errors.ProductoInexistenteError;
import com.example.demo.producto.domain.ProductoRepository;
import com.example.demo.shared.application.CommandHandler;
import com.example.demo.shared.application.Inject;
import com.example.demo.shared.domain.EventBus;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Inject
public class EliminarProducto implements CommandHandler<EliminarProductoCommand> {

    private final ProductoRepository productoRepository;
    private final EventBus eventBus;

    @Transactional
    @Override
    public void handle(EliminarProductoCommand command) {
        Producto productoEliminable = productoRepository.findById(command.getId())
                .orElseThrow(ProductoInexistenteError::new);

        Producto productoEliminado = productoEliminable.eliminar();
        productoRepository.delete(productoEliminado);
        eventBus.publish(productoEliminado.pullEvents());
    }
}
