package com.example.demo.stock.application;

import com.example.demo.shared.application.CommandHandler;
import com.example.demo.shared.application.Inject;
import com.example.demo.stock.domain.Stock;
import com.example.demo.stock.domain.StockRepository;
import com.example.demo.stock.domain.commands.CrearStockCommand;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Inject
public class CrearStock implements CommandHandler<CrearStockCommand> {

    private final StockRepository stockRepository;

    @Transactional
    @Override
    public void handle(CrearStockCommand command) {
        Stock stock = Stock.create(command);
        stockRepository.save(stock);
    }

}
