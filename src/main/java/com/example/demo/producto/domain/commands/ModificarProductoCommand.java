package com.example.demo.producto.domain.commands;

import com.example.demo.shared.domain.Command;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModificarProductoCommand implements Command {

    private final Long id;
    private final String codigo;
    private final String nombre;

}
