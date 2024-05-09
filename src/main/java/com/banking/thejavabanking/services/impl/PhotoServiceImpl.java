package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.repositories.PhotoRepository;
import com.banking.thejavabanking.services.IPhotoService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

@Service
@FieldDefaults(
        level = lombok.AccessLevel.PRIVATE,
        makeFinal = true
)
public class PhotoServiceImpl implements IPhotoService {
    Cloudinary cloudinary;
    PhotoRepository photoRepository;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "drpo8twra",
                "api_key", "344384456334146",
                "api_secret", "O5PAjxh9Gdyc6IHkSyJpEQfidCs"
        ));
    }

    @Override
    public Map upload(MultipartFile multipartFile) {
        try {
            File file = convert(multipartFile);
            Map result = cloudinary.uploader()
                                   .upload(file, ObjectUtils.emptyMap());
            if (!Files.deleteIfExists(file.toPath())) {
                throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convert(MultipartFile file) {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return convFile;
    }

    @Override
    public void delete(String id) throws IOException {
        cloudinary.uploader()
                  .destroy(id, ObjectUtils.emptyMap());

        photoRepository.deletePhotoByPublicId(id);
    }
}
