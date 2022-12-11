package com.example.demo.stock.application;

import com.example.demo.shared.application.Inject;
import com.example.demo.shared.application.QueryHandler;
import com.example.demo.stock.domain.Stock;
import com.example.demo.stock.domain.StockRepository;
import com.example.demo.stock.domain.errors.StockInexistenteError;
import com.example.demo.stock.domain.queries.BuscarStockQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Inject
public class BuscarStock implements QueryHandler<Stock, BuscarStockQuery> {

    private final StockRepository stockRepository;

    @Override
    public Stock handle(BuscarStockQuery query) {
        return stockRepository.findByProducto(query.getProducto())
                .orElseThrow(StockInexistenteError::new);
    }
}
