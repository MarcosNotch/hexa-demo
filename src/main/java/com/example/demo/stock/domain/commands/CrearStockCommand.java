package com.example.demo.stock.domain.commands;

import com.example.demo.shared.domain.Command;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CrearStockCommand implements Command {

    private final Long productoId;
}
