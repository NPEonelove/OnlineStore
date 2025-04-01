package ru.meowlove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.meowlove.catalogservice.dto.product.AddProduct;
import ru.meowlove.catalogservice.dto.product.GetProduct;
import ru.meowlove.catalogservice.model.Product;
import ru.meowlove.catalogservice.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public AddProduct addProduct(@RequestBody AddProduct addProduct) {
        Product product = modelMapper.map(addProduct, Product.class);
        System.out.println(product);
        productRepository.save(modelMapper.map(addProduct, Product.class));
        return addProduct;
    }

    public GetProduct getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        System.out.println(product);
        return modelMapper.map(product, GetProduct.class);
    }

//    public
}
