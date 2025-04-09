package ru.meowlove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meowlove.catalogservice.dto.category.AddCategory;
import ru.meowlove.catalogservice.dto.category.EditCategory;
import ru.meowlove.catalogservice.dto.category.GetCategory;
import ru.meowlove.catalogservice.dto.product.GetProduct;
import ru.meowlove.catalogservice.exception.category.CategoryAlreadyExistsException;
import ru.meowlove.catalogservice.exception.category.CategoryNotExistsException;
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
        if (categoryRepository.findByName(addCategory.getName()) != null) {
            categoryRepository.save(modelMapper.map(addCategory, Category.class));
        } else {
            throw new CategoryAlreadyExistsException("Category with name " + addCategory.getName() + " already exists");
        }
        return addCategory;
    }

    @Transactional
    public EditCategory editCategory(Long id, EditCategory editCategory) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotExistsException("Category does not exist"));
        category.setName(editCategory.getName());
        categoryRepository.save(category);
        return editCategory;
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new CategoryNotExistsException("Category does not exist"));
    }
}
