package ru.meowlove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meowlove.catalogservice.dto.category.AddCategory;
import ru.meowlove.catalogservice.dto.category.EditCategory;
import ru.meowlove.catalogservice.dto.category.GetCategory;
import ru.meowlove.catalogservice.dto.product.GetCardProduct;
import ru.meowlove.catalogservice.dto.product.GetFullProduct;
import ru.meowlove.catalogservice.exception.category.CategoryAlreadyExistsException;
import ru.meowlove.catalogservice.exception.category.CategoryNotExistsException;
import ru.meowlove.catalogservice.exception.product.ProductNotExistsException;
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

    public List<GetCardProduct> findByName(String name) {
         Category category = categoryRepository.findByName(name);
         List<Product> products = category.getProducts();
         List<GetCardProduct> productList = new ArrayList<>();
         for (Product product : products) {
             GetCardProduct getCardProduct = modelMapper.map(product, GetCardProduct.class);
             productList.add(getCardProduct);
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

    private void checkCategoryNameUnique(Long id, String name) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new ProductNotExistsException("Product with this id does not exist");
        }

        Category category = categoryRepository.findByName(name);

        if (category != null) {
            if (!category.getId().equals(id)) {
                throw new ProductNotExistsException("Product with this name already exist");
            }
        }
    }

    @Transactional
    public EditCategory editCategory(Long id, EditCategory editCategory) {

        checkCategoryNameUnique(id, editCategory.getName());

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
