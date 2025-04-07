package ru.meowlove.catalogservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.meowlove.catalogservice.exception.ProductErrorResponse;
import ru.meowlove.catalogservice.exception.product.ProductAlreadyExistsException;
import ru.meowlove.catalogservice.exception.product.ProductNotCreatedException;
import ru.meowlove.catalogservice.exception.product.ProductNotExistsException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ProductNotCreatedException.class)
    public ResponseEntity<ProductErrorResponse> handleProductNotCreatedException(ProductNotCreatedException ex) {
        return new ResponseEntity<>(new ProductErrorResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ProductErrorResponse> handleProductNotCreatedException(ProductAlreadyExistsException ex) {
        return new ResponseEntity<>(new ProductErrorResponse(ex.getMessage(), new Date()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductNotExistsException.class)
    public ResponseEntity<ProductErrorResponse> handleProductNotExistsException(ProductNotExistsException ex) {
        return new ResponseEntity<>(new ProductErrorResponse(ex.getMessage(), new Date()), HttpStatus.NOT_FOUND);
    }

}
