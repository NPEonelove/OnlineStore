package ru.meowlove.catalogservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.meowlove.catalogservice.dto.product.AddProduct;
import ru.meowlove.catalogservice.dto.product.EditProduct;
import ru.meowlove.catalogservice.dto.product.GetProduct;
import ru.meowlove.catalogservice.service.ProductService;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @SneakyThrows
    @PostMapping
    public AddProduct addProduct(@RequestPart("file") MultipartFile file,
                                 @RequestPart("meta") String addProduct) {
        ObjectMapper mapper = new ObjectMapper();
        AddProduct product = mapper.readValue(addProduct, AddProduct.class);
        return productService.addProduct(file, product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProduct> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EditProduct> updateProduct(@RequestBody EditProduct editProduct,
                                                     @PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.editProduct(id, editProduct));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return HttpStatus.OK;
    }
}
