package com.example.demo.stock.presentation;

import com.example.demo.shared.application.CommandBus;
import com.example.demo.stock.domain.commands.CrearStockCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StockListener {

    private final CommandBus commandBus;

    @RabbitListener(queues = {"q-producto-creado"})
    void productoCreadoListener(@Payload ProductoCreadoEventDTO event) {
        CrearStockCommand command = CrearStockCommand.builder()
                .productoId(event.getProductoId())
                .build();

        commandBus.handle(command);
    }

}
