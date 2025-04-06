package ru.meowlove.catalogservice.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ru.meowlove.catalogservice.dto.category.GetCategory;

import java.util.List;

@Getter
@Setter
public class AddProduct {
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(min = 1, max = 2000)
    private String description;

    @NotNull
    @Min(0)
    private Double price;

    private String photo;

    @NotNull
    @Min(0)
    private Integer count;

    private List<GetCategory> categories;
}
