package com.example.demo.producto.domain.commands;

import com.example.demo.shared.domain.Command;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EliminarProductoCommand implements Command {

    private final Long id;

}
