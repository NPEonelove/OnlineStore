package ru.meowlove.catalogservice.exception.product;

public class ProductNotEditedException extends RuntimeException {
    public ProductNotEditedException(String message) {
        super(message);
    }
}
