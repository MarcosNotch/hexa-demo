package com.example.demo.stock.domain.queries;

import com.example.demo.shared.domain.Query;
import com.example.demo.stock.domain.Stock;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BuscarStockQuery implements Query<Stock> {

    private final Long producto;
}
