package ru.meowlove.catalogservice.dto.product;

import lombok.Getter;
import lombok.Setter;
import ru.meowlove.catalogservice.dto.category.GetCategory;

import java.util.List;

@Getter
@Setter
public class GetProduct {
    private String name;

    private String description;

    private Double price;

    private String photo;

    private Integer count;

    private Double rating;

    List<GetCategory> categories;
}
