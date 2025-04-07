package ru.meowlove.catalogservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.meowlove.catalogservice.dto.product.AddProduct;
import ru.meowlove.catalogservice.dto.product.EditProduct;
import ru.meowlove.catalogservice.dto.product.GetProduct;
import ru.meowlove.catalogservice.exception.product.ProductNotCreatedException;
import ru.meowlove.catalogservice.service.ProductService;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<AddProduct> addProduct(@RequestParam(value = "file", required = false) MultipartFile file,
                                        @ModelAttribute @Valid AddProduct addProduct, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMsg.append(error.getDefaultMessage()).append(". ");
            }
            throw new ProductNotCreatedException(errorMsg.toString());
        }

        if (file == null) {
            productService.addProduct(addProduct);
        } else {
            productService.addProduct(file, addProduct);
        }

        return ResponseEntity.ok(addProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProduct> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateProduct(@RequestParam(value = "file", required = false) MultipartFile file,
                                                    @ModelAttribute EditProduct editProduct, @PathVariable Long id) {
        if (file == null) {
            productService.editProduct(id, editProduct);
        } else {
            productService.editProduct(id, file, editProduct);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return HttpStatus.OK;
    }
}
