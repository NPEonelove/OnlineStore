package ru.meowlove.catalogservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.meowlove.catalogservice.exception.CategoryErrorResponse;
import ru.meowlove.catalogservice.exception.ProductErrorResponse;
import ru.meowlove.catalogservice.exception.category.CategoryAlreadyExistsException;
import ru.meowlove.catalogservice.exception.category.CategoryNotCreatedException;
import ru.meowlove.catalogservice.exception.category.CategoryNotEditedException;
import ru.meowlove.catalogservice.exception.category.CategoryNotExistsException;
import ru.meowlove.catalogservice.exception.product.ProductAlreadyExistsException;
import ru.meowlove.catalogservice.exception.product.ProductNotCreatedException;
import ru.meowlove.catalogservice.exception.product.ProductNotEditedException;
import ru.meowlove.catalogservice.exception.product.ProductNotExistsException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController {

    //product
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

    @ExceptionHandler(ProductNotEditedException.class)
    public ResponseEntity<ProductErrorResponse> handleProductNotExistsException(ProductNotEditedException ex) {
        return new ResponseEntity<>(new ProductErrorResponse(ex.getMessage(), new Date()), HttpStatus.CONFLICT);
    }

    //category
    @ExceptionHandler(CategoryNotCreatedException.class)
    public ResponseEntity<CategoryErrorResponse> handleProductNotExistsException(CategoryNotCreatedException ex) {
        return new ResponseEntity<>(new CategoryErrorResponse(ex.getMessage(), new Date()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryNotExistsException.class)
    public ResponseEntity<CategoryErrorResponse> handleProductNotExistsException(CategoryNotExistsException ex) {
        return new ResponseEntity<>(new CategoryErrorResponse(ex.getMessage(), new Date()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<CategoryErrorResponse> handleProductNotExistsException(CategoryAlreadyExistsException ex) {
        return new ResponseEntity<>(new CategoryErrorResponse(ex.getMessage(), new Date()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotEditedException.class)
    public ResponseEntity<CategoryErrorResponse> handleProductNotExistsException(CategoryNotEditedException ex) {
        return new ResponseEntity<>(new CategoryErrorResponse(ex.getMessage(), new Date()), HttpStatus.NOT_FOUND);
    }

}
