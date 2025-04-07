package ru.meowlove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.meowlove.catalogservice.client.MediaClient;
import ru.meowlove.catalogservice.dto.category.GetCategory;
import ru.meowlove.catalogservice.dto.product.AddProduct;
import ru.meowlove.catalogservice.dto.product.EditProduct;
import ru.meowlove.catalogservice.dto.product.GetProduct;
import ru.meowlove.catalogservice.exception.product.ProductAlreadyExistsException;
import ru.meowlove.catalogservice.exception.product.ProductNotExistsException;
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
    private final MediaClient mediaClient;

    @SneakyThrows
    @Transactional
    public void addProduct(MultipartFile file, AddProduct addProduct) {

        if (!productRepository.findByName(addProduct.getName()).isEmpty()) {
            throw new ProductAlreadyExistsException("Product already exists");
        }

        Product product = mapAddProduct(addProduct);

        String linkPhoto = mediaClient.uploadMedia("catalog", file);
        product.setPhoto(linkPhoto);

        productRepository.save(product);
    }

    @SneakyThrows
    @Transactional
    public void addProduct(AddProduct addProduct) {
        if (!productRepository.findByName(addProduct.getName()).isEmpty()) {
            throw new ProductAlreadyExistsException("Product already exists");
        }

        productRepository.save(mapAddProduct(addProduct));
    }

    @SneakyThrows
    private Product mapAddProduct(AddProduct addProduct) {

        Product product = new Product();
        product.setName(addProduct.getName());
        product.setDescription(addProduct.getDescription());
        product.setPrice(addProduct.getPrice());
        product.setCount(addProduct.getCount());

        List<GetCategory> categories = addProduct.getCategories();
        if (categories != null && !categories.isEmpty()) {
            List<Category> categoryList = new ArrayList<>();
            for (GetCategory getCategory : categories) {
                categoryList.add(categoryRepository.findByName(modelMapper.map(getCategory, Category.class).getName()));
            }

            product.setCategories(categoryList);
        }

        return product;
    }

    public GetProduct getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return modelMapper.map(product, GetProduct.class);
    }

    private Product mapEditProduct(Long id, EditProduct updateProduct) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(updateProduct.getName());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());
        product.setCount(updateProduct.getCount());
        List<GetCategory> categories = updateProduct.getCategories();
        List<Category> categoryList = new ArrayList<>();
        for (GetCategory getCategory : categories) {
            categoryList.add(categoryRepository.findByName(modelMapper.map(getCategory, Category.class).getName()));
        }
        product.setCategories(categoryList);

        return product;
    }

    @SneakyThrows
    @Transactional
    public void editProduct(Long id, EditProduct updateProduct) {

        Product product = mapEditProduct(id, updateProduct);

        product.setPhoto(updateProduct.getPhoto());
        productRepository.save(product);
    }

    @SneakyThrows
    @Transactional
    public void editProduct(Long id, MultipartFile file, EditProduct updateProduct) {

        Product product = mapEditProduct(id, updateProduct);

        if (product.getPhoto() != null) {
            mediaClient.deleteMedia(product.getPhoto());
        }
        String photo = mediaClient.uploadMedia("catalog", file);
        product.setPhoto(photo);

        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            Product product = productRepository.findById(id).orElseThrow();
            mediaClient.deleteMedia(product.getPhoto());
            productRepository.deleteById(id);
        } else {
            throw new ProductNotExistsException("Product not exists");
        }
    }
}
