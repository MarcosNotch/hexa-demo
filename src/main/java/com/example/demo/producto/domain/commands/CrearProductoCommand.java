package com.example.demo.producto.domain.commands;

import com.example.demo.shared.domain.Command;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CrearProductoCommand implements Command {

    private final String codigo;
    private final String nombre;

}
