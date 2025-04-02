package ru.meowlove.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.meowlove.catalogservice.dto.category.AddCategory;
import ru.meowlove.catalogservice.dto.category.GetCategory;
import ru.meowlove.catalogservice.dto.product.GetProduct;
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
    public ResponseEntity<List<GetProduct>> getProductsByCategory(@RequestBody GetCategory getCategory) {
        return ResponseEntity.ok(categoryService.findByName(getCategory.getName()));
    }

    @PostMapping
    public ResponseEntity<AddCategory> addCategory(@RequestBody AddCategory addCategory) {
        return ResponseEntity.ok(categoryService.addCategory(addCategory));
    }
}
