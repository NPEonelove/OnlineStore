package ru.meowlove.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.meowlove.catalogservice.dto.product.AddProduct;
import ru.meowlove.catalogservice.dto.product.GetProduct;
import ru.meowlove.catalogservice.service.ProductService;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public AddProduct addProduct(@RequestBody AddProduct addProduct) {
        return productService.addProduct(addProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProduct> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }
}
