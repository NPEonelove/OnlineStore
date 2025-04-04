package ru.meowlove.catalogservice.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCategory {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;
}
