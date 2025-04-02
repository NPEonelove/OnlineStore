package ru.meowlove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meowlove.catalogservice.dto.category.GetCategory;
import ru.meowlove.catalogservice.dto.product.AddProduct;
import ru.meowlove.catalogservice.dto.product.EditProduct;
import ru.meowlove.catalogservice.dto.product.GetProduct;
import ru.meowlove.catalogservice.model.Category;
import ru.meowlove.catalogservice.model.Product;
import ru.meowlove.catalogservice.repository.CategoryRepository;
import ru.meowlove.catalogservice.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public AddProduct addProduct(AddProduct addProduct) {
        Product product = new Product();
        product.setName(addProduct.getName());
        product.setDescription(addProduct.getDescription());
        product.setPrice(addProduct.getPrice());
        product.setPhoto(addProduct.getPhoto());
        product.setCount(addProduct.getCount());
        List<GetCategory> categories = addProduct.getCategories();
        List<Category> categoryList = new ArrayList<>();
        for (GetCategory getCategory : categories) {
            categoryList.add(categoryRepository.findByName(modelMapper.map(getCategory, Category.class).getName()));
        }

        product.setCategories(categoryList);
        productRepository.save(product);
        return addProduct;
    }

    public GetProduct getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return modelMapper.map(product, GetProduct.class);
    }

    @Transactional
    public EditProduct editProduct(Long id, EditProduct editProduct) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(editProduct.getName());
        product.setDescription(editProduct.getDescription());
        product.setPrice(editProduct.getPrice());
        product.setPhoto(editProduct.getPhoto());
        product.setCount(editProduct.getCount());
        List<GetCategory> categories = editProduct.getCategories();
        List<Category> categoryList = new ArrayList<>();
        for (GetCategory getCategory : categories) {
            categoryList.add(categoryRepository.findByName(modelMapper.map(getCategory, Category.class).getName()));
        }

        product.setCategories(categoryList);
//        product.setCategories(editProduct.getCategories().stream().map(category ->
//                modelMapper.map(category, Category.class)).collect(Collectors.toList()));
        return modelMapper.map(productRepository.save(product), EditProduct.class);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
        // TODO: throw
    }
}
