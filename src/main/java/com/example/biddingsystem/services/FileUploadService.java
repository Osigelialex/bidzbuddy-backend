package com.example.biddingsystem.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    public String uploadImage(MultipartFile multipartFile) throws IOException;
}
