package com.acunsoz.ecommerce_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Cloudinary motorunu otomatik içeri almak için gerekli
public class FileService {
    private final Cloudinary cloudinary;
    //private String UPLOAD_DIR = "uploads/images/"; //lokal için

    public String saveImages(MultipartFile file) throws IOException
    {
        if (file.isEmpty()){
            throw new RuntimeException("Lütfen gecerli bir resim dosyasi yukleyin");
        }
        /* local için olan eski kod
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

         */

        // ESKİ KOD: Path uploadPath = Paths.get(UPLOAD_DIR); ... (SİLİNDİ)
        // Çünkü artık dosyaları "uploads/images/" klasörüne değil, Cloudinary'ye atıyoruz.

        // YENİ KOD: Cloudinary'ye Yükleme İşlemi
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("public_id", UUID.randomUUID().toString())
        );

        // Eski kod sadece dosya adını dönüyordu.
        // Yeni kod ise Cloudinary'nin oluşturduğu "https://res.cloudinary.com/..." şeklindeki tam URL'yi dönüyor.
        return uploadResult.get("secure_url").toString();

    }

}
