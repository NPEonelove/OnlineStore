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
import ru.meowlove.catalogservice.dto.product.GetCardProduct;
import ru.meowlove.catalogservice.dto.product.GetFullProduct;
import ru.meowlove.catalogservice.exception.category.CategoryNotExistsException;
import ru.meowlove.catalogservice.exception.product.ProductAlreadyExistsException;
import ru.meowlove.catalogservice.exception.product.ProductNotExistsException;
import ru.meowlove.catalogservice.model.Category;
import ru.meowlove.catalogservice.model.Product;
import ru.meowlove.catalogservice.repository.CategoryRepository;
import ru.meowlove.catalogservice.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final MediaClient mediaClient;

    public List<GetCardProduct> getAllProducts() {
//        List<GetCardProduct> products = new ArrayList<>();
//        List<Product> productList = productRepository.findAll();
//        for (Product product : productList) {
//            GetCardProduct getCardProduct = new GetCardProduct();
//            getCardProduct
//        }
        return productRepository.findAll().stream().map(product ->
                modelMapper.map(product, GetCardProduct.class)).collect(Collectors.toList());
    }

    @SneakyThrows
    @Transactional
    public void addProduct(MultipartFile file, AddProduct addProduct) {

        if (productRepository.findByName(addProduct.getName()) != null) {
            throw new ProductAlreadyExistsException("Product already exists");
        }

        Product product = mapAddProduct(addProduct);

        String linkPhoto = mediaClient.uploadMedia("catalog/productCard", Collections.singletonList(file)
                .toArray(new MultipartFile[0])).getFirst();
        product.setPhoto(linkPhoto);

        productRepository.save(product);
    }

    @SneakyThrows
    @Transactional
    public void addProduct(AddProduct addProduct) {
        if (productRepository.findByName(addProduct.getName()) != null) {
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

    public GetFullProduct getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotExistsException("Product does not exist"));
        return modelMapper.map(product, GetFullProduct.class);
    }

    private Product mapEditProduct(Long id, EditProduct updateProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotExistsException("Product does not exist"));
        product.setName(updateProduct.getName());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());
        product.setCount(updateProduct.getCount());
        List<GetCategory> categories = updateProduct.getCategories();
        if (categories != null && !categories.isEmpty()) {
            List<Category> categoryList = new ArrayList<>();

            for (GetCategory getCategory : categories) {
                categoryList.add(categoryRepository.findByName(modelMapper.map(getCategory, Category.class).getName()));
            }

            product.setCategories(categoryList);
        }

        return product;
    }

    private void checkProductNameUnique(Long id, String name) {
        if (productRepository.findById(id).isEmpty()) {
            throw new CategoryNotExistsException("Product with this id does not exist");
        }

        Product product = productRepository.findByName(name);

        if (product != null) {
            if (!product.getId().equals(id)) {
                throw new CategoryNotExistsException("Product with this name already exist");
            }
        }
    }

    @SneakyThrows
    @Transactional
    public void editProduct(Long id, EditProduct updateProduct) {

        checkProductNameUnique(id, updateProduct.getName());

        Product product = mapEditProduct(id, updateProduct);

        product.setPhoto(updateProduct.getPhoto());

        productRepository.save(product);
    }

    @SneakyThrows
    @Transactional
    public void editProduct(Long id, MultipartFile file, EditProduct updateProduct) {

        checkProductNameUnique(id, updateProduct.getName());

        Product product = mapEditProduct(id, updateProduct);

        if (product.getPhoto() != null) {
            mediaClient.deleteMedia(Collections.singletonList(product.getPhoto()).toArray(new String[0]));
        }
        String photo = mediaClient.uploadMedia("catalog/productCard", Collections.singletonList(file)
                .toArray(new MultipartFile[0])).getFirst();
        product.setPhoto(photo);

        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotExistsException("Product does not exist"));
        mediaClient.deleteMedia(Collections.singletonList(product.getPhoto()).toArray(new String[0]));
        productRepository.deleteById(id);
    }
}
