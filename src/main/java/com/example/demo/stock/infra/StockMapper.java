package com.example.demo.stock.infra;

import com.example.demo.stock.domain.CantidadStock;
import com.example.demo.stock.domain.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public Stock toDomain(StockEntity entity) {
        return Stock.builder()
                .uuid(entity.getUuid())
                .productoId(entity.getProductoId())
                .stock(new CantidadStock(entity.getStock()))
                .build();
    }

    public StockEntity toEntity(Stock domain) {
        return StockEntity.builder()
                .uuid(domain.getUuid())
                .productoId(domain.getProductoId())
                .stock(domain.getStock().getValue())
                .build();
    }

}
