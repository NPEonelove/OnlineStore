package ru.meowlove.catalogservice.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProduct {
    private String name;
    private String description;
    private Double price;
    private String photo;
    private Integer count;
}
