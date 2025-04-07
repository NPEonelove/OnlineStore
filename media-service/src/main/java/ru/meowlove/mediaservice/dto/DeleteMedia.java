package ru.meowlove.mediaservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteMedia {
    String bucketName;
    String key;
}
