package com.example.demo.stock.infra;

import com.example.demo.stock.domain.Stock;
import com.example.demo.stock.domain.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class StockRepositoryAdapter implements StockRepository {

    private final StockRepositoryJPA stockRepositoryJPA;
    private final StockMapper mapper;

    @Override
    public void save(Stock stock) {
        stockRepositoryJPA.save(mapper.toEntity(stock));
    }

    @Override
    public Optional<Stock> findByProducto(Long producto) {
        return stockRepositoryJPA.findFirstByProductoId(producto)
                .map(mapper::toDomain);
    }
}
