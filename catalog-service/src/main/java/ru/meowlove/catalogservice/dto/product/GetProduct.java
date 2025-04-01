package ru.meowlove.catalogservice.dto.product;

import java.util.List;

public class GetProduct {
    String name;
    String description;
    Double price;
    String photo;
    Integer count;
    Double rating;
    List<GetProduct> products;
}
