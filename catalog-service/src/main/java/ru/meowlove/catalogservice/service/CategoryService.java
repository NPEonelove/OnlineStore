package ru.meowlove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meowlove.catalogservice.dto.category.AddCategory;
import ru.meowlove.catalogservice.dto.category.GetCategory;
import ru.meowlove.catalogservice.dto.product.GetProduct;
import ru.meowlove.catalogservice.model.Category;
import ru.meowlove.catalogservice.model.Product;
import ru.meowlove.catalogservice.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<GetCategory> findAll() {
        return categoryRepository.findAll().stream().map(category ->
                modelMapper.map(category, GetCategory.class))
                .collect(Collectors.toList());
    }

    public List<GetProduct> findByName(String name) {
         Category category = categoryRepository.findByName(name);
         List<Product> products = category.getProducts();
         List<GetProduct> productList = new ArrayList<>();
         for (Product product : products) {
             GetProduct getProduct = modelMapper.map(product, GetProduct.class);
             productList.add(getProduct);
         }
         return productList;
    }

    @Transactional
    public AddCategory addCategory(AddCategory addCategory) {
        categoryRepository.save(modelMapper.map(addCategory, Category.class));
        return addCategory;
    }
}
