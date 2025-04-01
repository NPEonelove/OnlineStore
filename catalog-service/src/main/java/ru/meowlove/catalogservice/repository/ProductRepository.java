package ru.meowlove.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meowlove.catalogservice.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
