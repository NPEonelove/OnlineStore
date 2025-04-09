package ru.meowlove.mediaservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final S3Client s3Client;

    @Value("${yandex.s3.bucket}")
    private String bucket;

    @SneakyThrows
    public String uploadFile(String directory, MultipartFile file) {
        String key = directory + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename() + "-" + System.currentTimeMillis();

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );


        return "https://storage.yandexcloud.net/" + bucket + "/" + key;
    }

    public void deleteFile(String keyName) {
        List<String> splits = Arrays.asList(keyName.split("/"));
        keyName = splits.get(4) + "/" + splits.get(5);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(keyName).build();
        s3Client.deleteObject(deleteObjectRequest);
    }

}
