package ru.meowlove.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.meowlove.catalogservice.dto.photo.GetPhoto;
import ru.meowlove.catalogservice.model.Photo;
import ru.meowlove.catalogservice.service.PhotoService;

import java.util.List;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/{id}")
    public ResponseEntity<List<GetPhoto>> getProductPhotos(@PathVariable("id") Long id) {
        return ResponseEntity.ok(photoService.getProductPhotos(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> addProductPhotos(@RequestPart("files") MultipartFile[] files,
                                                       @PathVariable("id") Long id) {
        photoService.saveProductPhotos(id, files);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAllProductPhotos(@PathVariable("id") Long id) {
        photoService.deleteAllProductPhotos(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteProductPhotos(@RequestBody Long[] ids) {
        photoService.deletePhotosById(ids);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
