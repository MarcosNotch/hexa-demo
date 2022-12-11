package com.example.demo.producto.application;

import com.example.demo.producto.domain.*;
import com.example.demo.producto.domain.commands.ModificarProductoCommand;
import com.example.demo.producto.domain.errors.CodigoProductoExistenteError;
import com.example.demo.producto.domain.errors.ProductoInexistenteError;
import com.example.demo.shared.application.CommandHandler;
import com.example.demo.shared.application.Inject;
import com.example.demo.shared.domain.EventBus;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Inject
public class ModificarProducto implements CommandHandler<ModificarProductoCommand> {

    private final ProductoRepository productoRepository;
    private final EventBus eventBus;

    @Transactional
    @Override
    public void handle(ModificarProductoCommand command) {
        Producto productoModificable = productoRepository.findById(command.getId())
                .orElseThrow(ProductoInexistenteError::new);
        validateCommand(productoModificable, command);
        Producto productoModificado = productoModificable.update(command);
        productoRepository.save(productoModificado);
        eventBus.publish(productoModificado.pullEvents());
    }

    private void validateCommand(Producto producto, ModificarProductoCommand command) {
        if(!producto.getCodigo().getValue().equals(command.getCodigo())
                && productoRepository.existsProductoWithCodigo(command.getCodigo())){
            throw new CodigoProductoExistenteError();
        }
    }

}
