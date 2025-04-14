package ru.meowlove.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.meowlove.catalogservice.service.PhotoService;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> addProductPhotos(@RequestPart("files") MultipartFile[] files,
                                                       @PathVariable("id") Long id) {



        return ResponseEntity.ok(HttpStatus.OK);
    }

}
