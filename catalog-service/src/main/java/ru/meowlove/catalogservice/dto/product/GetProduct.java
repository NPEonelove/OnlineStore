package ru.meowlove.catalogservice.dto.product;

import lombok.Getter;
import lombok.Setter;
import ru.meowlove.catalogservice.dto.category.GetCategory;

import java.util.List;

@Getter
@Setter
public class GetProduct {
    String name;
    String description;
    Double price;
    String photo;
    Integer count;
    Double rating;
    List<GetCategory> categories;
}
