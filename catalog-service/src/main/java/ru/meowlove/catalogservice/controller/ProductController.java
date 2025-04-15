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
import ru.meowlove.catalogservice.dto.product.GetCardProduct;
import ru.meowlove.catalogservice.dto.product.GetFullProduct;
import ru.meowlove.catalogservice.exception.product.ProductNotCreatedException;
import ru.meowlove.catalogservice.exception.product.ProductNotEditedException;
import ru.meowlove.catalogservice.model.Product;
import ru.meowlove.catalogservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<GetCardProduct>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

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
    public ResponseEntity<GetFullProduct> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateProduct(@PathVariable Long id, @RequestParam(value = "file", required = false) MultipartFile file,
                                                    @ModelAttribute @Valid EditProduct editProduct, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMsg.append(error.getDefaultMessage()).append(". ");
            }
            throw new ProductNotEditedException(errorMsg.toString());
        }

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
