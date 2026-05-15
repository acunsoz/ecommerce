package com.acunsoz.ecommerce_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private String UPLOAD_DIR = "uploads/images/";

    public String saveImages(MultipartFile file) throws IOException
    {
        if (file.isEmpty()){
            throw new RuntimeException("Lütfen gecerli bir resim dosyasi yukleyin");
        }

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath))
        {
            Files.createDirectories(uploadPath);
        }

        String orginalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + orginalFileName;

        Path filePath = uploadPath.resolve(uniqueFileName);

        Files.copy(file.getInputStream(),filePath);

        return uniqueFileName;

    }

}
