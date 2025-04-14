package ru.meowlove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.meowlove.catalogservice.client.MediaClient;
import ru.meowlove.catalogservice.model.Photo;
import ru.meowlove.catalogservice.repository.PhotoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final MediaClient mediaClient;

    public void saveProductPhotos(long id, MultipartFile[] files) {
        List<String> links = mediaClient.uploadMedia("/catalog/productPhotos", files);

    }



}
