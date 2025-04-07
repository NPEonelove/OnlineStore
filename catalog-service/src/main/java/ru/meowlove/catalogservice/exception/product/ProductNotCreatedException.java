package ru.meowlove.catalogservice.exception.product;

public class ProductNotCreatedException extends RuntimeException {
    public ProductNotCreatedException(String message) {
        super(message);
    }
}
