package ru.meowlove.mediaservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.meowlove.mediaservice.dto.DeleteMedia;
import ru.meowlove.mediaservice.service.MediaService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/media")
public class MediaController {
    private final MediaService mediaService;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<String> uploadMedia(@RequestPart("directory") String directory, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(mediaService.uploadFile(directory, file));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteMedia(@RequestParam("key") String key) {
        mediaService.deleteFile(key);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
