package com.example.demo.shared.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {

    private String codigo;
    private String mensaje;

}
