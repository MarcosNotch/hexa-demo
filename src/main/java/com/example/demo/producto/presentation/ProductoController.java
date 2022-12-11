package com.example.demo.producto.presentation;

import com.example.demo.producto.domain.queries.BuscarProductoQuery;
import com.example.demo.producto.domain.commands.CrearProductoCommand;
import com.example.demo.producto.domain.commands.EliminarProductoCommand;
import com.example.demo.producto.domain.commands.ModificarProductoCommand;
import com.example.demo.shared.application.CommandBus;
import com.example.demo.shared.application.QueryBus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(value = "productos")
public class ProductoController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody PostProductoRequest request) {
        CrearProductoCommand command = CrearProductoCommand.builder()
                .codigo(request.getCodigo())
                .nombre(request.getNombre())
                .build();

        commandBus.handle(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@Valid @PathVariable("id") Long id) {
        BuscarProductoQuery query = BuscarProductoQuery.builder()
                .id(id)
                .build();

        ProductoDTO dto = ProductoDTO.fromDomain(queryBus.handle(query));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateById(@Valid @PathVariable("id") Long id, @Valid @RequestBody PutProductoRequest request) {
        ModificarProductoCommand command = ModificarProductoCommand.builder()
                .id(id)
                .codigo(request.getCodigo())
                .nombre(request.getNombre())
                .build();

        commandBus.handle(command);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@Valid @PathVariable("id") Long id) {
        EliminarProductoCommand command = EliminarProductoCommand.builder()
                .id(id)
                .build();

        commandBus.handle(command);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
