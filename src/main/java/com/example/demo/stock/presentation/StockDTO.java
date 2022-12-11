package com.example.demo.stock.presentation;

import com.example.demo.stock.domain.Stock;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StockDTO {

    private Long producto;
    private Long cantidad;

    public static StockDTO fromDomain(Stock domain) {
        return StockDTO.builder()
                .producto(domain.getProductoId())
                .cantidad(domain.getStock().getValue())
                .build();
    }

}
