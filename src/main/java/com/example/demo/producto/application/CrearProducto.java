package com.example.demo.producto.application;

import com.example.demo.producto.domain.*;
import com.example.demo.producto.domain.commands.CrearProductoCommand;
import com.example.demo.producto.domain.errors.CodigoProductoExistenteError;
import com.example.demo.shared.application.CommandHandler;
import com.example.demo.shared.application.Inject;
import com.example.demo.shared.domain.EventBus;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Inject
public class CrearProducto implements CommandHandler<CrearProductoCommand> {

    private final ProductoRepository productoRepository;
    private final EventBus eventBus;

    @Transactional
    @Override
    public void handle(CrearProductoCommand command) {
        validateCommand(command);
        Long id = productoRepository.generateId();
        Producto producto = Producto.create(id, command);
        productoRepository.save(producto);
        eventBus.publish(producto.pullEvents());
    }

    private void validateCommand(CrearProductoCommand command) {
        if(productoRepository.existsProductoWithCodigo(command.getCodigo())){
            throw new CodigoProductoExistenteError();
        }
    }

}
