package com.example.demo.shared.presentation;

import com.example.demo.shared.domain.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractRestControllerAdvice {

    public abstract String servicePrefix();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> onException(Exception e) {
        ErrorResponse response = new ErrorResponse(servicePrefix() + "9000", e.getLocalizedMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> onBusinessException(BusinessException e) {
        ErrorResponse response = new ErrorResponse(servicePrefix() + "9001", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }


}
