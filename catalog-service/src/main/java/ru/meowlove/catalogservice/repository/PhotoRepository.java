package ru.meowlove.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meowlove.catalogservice.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
