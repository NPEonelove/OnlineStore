package ru.meowlove.catalogservice.exception.category;

public class CategoryNotCreatedException extends RuntimeException {
    public CategoryNotCreatedException(String message) {
        super(message);
    }
}
