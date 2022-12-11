package com.example.demo.producto.domain.events;

import com.example.demo.producto.domain.Producto;
import com.example.demo.shared.domain.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductoCreadoEvent extends DomainEvent {

    private final Producto producto;

}
