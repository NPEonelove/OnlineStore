package ru.meowlove.catalogservice.exception.category;

public class CategoryAlreadyExistsException extends RuntimeException {
  public CategoryAlreadyExistsException(String message) {
    super(message);
  }
}
