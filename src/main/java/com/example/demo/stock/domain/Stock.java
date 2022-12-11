package com.example.demo.stock.domain;

import com.example.demo.shared.domain.Aggregate;
import com.example.demo.stock.domain.commands.CrearStockCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class Stock extends Aggregate {

    private final UUID uuid;
    private final Long productoId;
    private final CantidadStock stock;

    public static Stock create(CrearStockCommand command) {
        Stock stock = Stock.builder()
                .uuid(UUID.randomUUID())
                .productoId(command.getProductoId())
                .stock(new CantidadStock(0L))
                .build();

        return stock;
    }
}
