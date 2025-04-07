package ru.meowlove.catalogservice.exception.product;

public class ProductNotExistsException extends RuntimeException {
    public ProductNotExistsException(String message) {
        super(message);
    }
}
