package ru.meowlove.catalogservice.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCategory {

    @NotNull(message = "Введите название")
    @Size(max = 255, message = "Длина названия максимум 255 символов")
    private String name;
}
