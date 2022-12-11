package com.example.demo.producto.infra.events;

import com.example.demo.producto.domain.events.ProductoCreadoEvent;
import com.example.demo.shared.infra.IntegrationEvent;
import com.example.demo.shared.infra.RegisteredEvent;
import lombok.Getter;

@Getter
@RegisteredEvent(domainEvent = ProductoCreadoEvent.class)
public class ProductoCreadoIntegrationEvent extends IntegrationEvent<ProductoCreadoEvent> {

    private final static String EVENT_NAME = "producto.creado";
    private final static String EXCHANGE_NAME = "x-demo-producto";

    private final Long productoId;

    public ProductoCreadoIntegrationEvent(ProductoCreadoEvent domainEvent) {
        super(domainEvent);
        this.productoId = domainEvent.getProducto().getId();
    }

    @Override
    public String eventName() {
        return EVENT_NAME;
    }

    @Override
    public String exchange() {
        return EXCHANGE_NAME;
    }
}
