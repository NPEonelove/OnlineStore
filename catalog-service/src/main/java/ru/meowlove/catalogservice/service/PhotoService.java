package ru.meowlove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.meowlove.catalogservice.client.MediaClient;
import ru.meowlove.catalogservice.dto.photo.GetPhoto;
import ru.meowlove.catalogservice.model.Photo;
import ru.meowlove.catalogservice.model.Product;
import ru.meowlove.catalogservice.repository.PhotoRepository;
import ru.meowlove.catalogservice.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final ProductRepository productRepository;
    private final MediaClient mediaClient;

    @Transactional
    public void saveProductPhotos(long id, MultipartFile[] files) {
        List<String> links = mediaClient.uploadMedia("catalog/productPhotos", files);

        Product product = productRepository.findById(id).get();

        for (String link : links) {
            Photo photo = new Photo();
            photo.setProduct(product);
            photo.setPhotoLink(link);
            photoRepository.save(photo);
        }
    }

    public List<GetPhoto> getProductPhotos(long id) {
        List<Photo> photos = photoRepository.findPhotoByProductId(id);
        List<GetPhoto> getPhotos = new ArrayList<>();
        for (Photo photo : photos) {
            GetPhoto getPhoto = new GetPhoto();
            getPhoto.setId(photo.getId());
            getPhoto.setProductId(photo.getProduct().getId());
            getPhoto.setPhotolink(photo.getPhotoLink());
            getPhotos.add(getPhoto);
        }
        return getPhotos;
    }

    @Transactional
    public void deleteAllProductPhotos(long id) {
        List<Photo> photos = photoRepository.findPhotoByProductId(id);
        for (Photo photo : photos) {
            mediaClient.deleteMedia(Collections.singletonList(photo.getPhotoLink()).toArray(new String[0]));
            photoRepository.removePhotoById(photo.getId());
        }
    }

    @Transactional
    public void deletePhotosById(Long[] ids) {
        for (Long id : ids) {
            mediaClient.deleteMedia(Collections.singletonList(photoRepository.findPhotoById(id).getPhotoLink()).toArray(new String[0]));
            photoRepository.removePhotoById(id);
        }
    }
}
