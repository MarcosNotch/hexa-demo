package com.example.demo.stock.domain;

import java.util.Optional;

public interface StockRepository {

    void save(Stock stock);

    Optional<Stock> findByProducto(Long producto);
}
