package ru.meowlove.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meowlove.catalogservice.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    List<Product> (Long id);
    Category findByName(String name);
}
