package ru.meowlove.catalogservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.meowlove.catalogservice.dto.category.AddCategory;
import ru.meowlove.catalogservice.dto.category.EditCategory;
import ru.meowlove.catalogservice.dto.category.GetCategory;
import ru.meowlove.catalogservice.dto.product.GetCardProduct;
import ru.meowlove.catalogservice.dto.product.GetFullProduct;
import ru.meowlove.catalogservice.exception.category.CategoryNotCreatedException;
import ru.meowlove.catalogservice.exception.category.CategoryNotEditedException;
import ru.meowlove.catalogservice.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<GetCategory>> getCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping
    public ResponseEntity<List<GetCardProduct>> getProductsByCategory(@RequestBody GetCategory getCategory) {
        return ResponseEntity.ok(categoryService.findByName(getCategory.getName()));
    }

    @PostMapping
    public ResponseEntity<AddCategory> addCategory(@RequestBody @Valid AddCategory addCategory, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMsg.append(fieldError.getDefaultMessage());
            }
            throw new CategoryNotCreatedException(errorMsg.toString());
        }
        return ResponseEntity.ok(categoryService.addCategory(addCategory));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EditCategory> editCategory(@PathVariable Long id, @RequestBody @Valid EditCategory editCategory,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMsg.append(fieldError.getDefaultMessage());
            }
            throw new CategoryNotEditedException(errorMsg.toString());
        }
        return ResponseEntity.ok(categoryService.editCategory(id, editCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
