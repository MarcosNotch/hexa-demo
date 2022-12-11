package com.example.demo.stock.presentation;

import com.example.demo.shared.application.CommandBus;
import com.example.demo.shared.application.QueryBus;
import com.example.demo.stock.domain.queries.BuscarStockQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(value = "stock")
public class StockController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @GetMapping("{producto}")
    public ResponseEntity<?> get(@Valid @PathVariable Long producto) {
        BuscarStockQuery buscarStockQuery = BuscarStockQuery.builder()
                .producto(producto)
                .build();

        StockDTO dto = StockDTO.fromDomain(queryBus.handle(buscarStockQuery));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

}
