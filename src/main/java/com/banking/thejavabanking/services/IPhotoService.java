package com.banking.thejavabanking.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface IPhotoService {
    Map upload(MultipartFile file);

    void delete(String publicId) throws IOException;
}
