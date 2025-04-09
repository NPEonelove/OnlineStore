package ru.meowlove.catalogservice.exception.category;

public class CategoryNotEditedException extends RuntimeException {
    public CategoryNotEditedException(String message) {
        super(message);
    }
}
