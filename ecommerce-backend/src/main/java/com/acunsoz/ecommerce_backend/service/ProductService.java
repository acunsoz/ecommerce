package com.acunsoz.ecommerce_backend.service;

import com.acunsoz.ecommerce_backend.exception.ProductNotFoundException;
import com.acunsoz.ecommerce_backend.model.dto.ProductDTO;
import com.acunsoz.ecommerce_backend.model.entity.Category;
import com.acunsoz.ecommerce_backend.model.entity.Product;
import com.acunsoz.ecommerce_backend.model.entity.User;
import com.acunsoz.ecommerce_backend.model.mapper.IProductMapper;
import com.acunsoz.ecommerce_backend.repository.CategoryRepository;
import com.acunsoz.ecommerce_backend.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final IProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;


    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO, User user)
    {
        // 1. Garsonun getirdiği sipariş fişini (DTO), mutfaktaki çiğ ete (Entity) çeviriyoruz
        Product product = productMapper.toEntity(productDTO);

        // 2. Depoya inip kullanıcının gönderdiği ID'ye (örneğin 1) sahip GERÇEK kategoriyi buluyoruz
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Bu id ye ait bir kategori bulunamadı! "));

        // 3. Bulduğumuz bu gerçek kategoriyi, ürünümüze (çiğ ete) sağlamca bağlıyoruz
        product.setCategory(category);

        // GÜVENLİK MÜHRÜ: Ürünün sahibi bu admin'dir!
        product.setAdminId(user.getId());

        // 4. Ürünü fırına veriyoruz (Veritabanına güvenle kaydediyoruz)
        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    public Page<ProductDTO> getAllProducts(Long categoryId,int page, int size)
    {
        Pageable pageable = (Pageable) PageRequest.of(page, size);

        Page<Product> productPage;

        // Kategori seçildiyse sadece onları getir, seçilmediyse hepsini getir
        if (categoryId != null) {
            productPage = productRepository.findByCategoryId(categoryId, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        return productPage.map(product -> productMapper.toDto(product));


    }

    public void deleteProduct(Long id,User user)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Silinecek ürün bulunamadı id: " + id));

        // GÜVENLİK KİLİDİ: Bu ürün bu admin'e mi ait?
        if (!product.getAdminId().equals(user.getId())) {
            throw new RuntimeException("Yetkisiz İşlem: Sadece kendi eklediğiniz ürünleri silebilirsiniz!");
        }

        productRepository.deleteById(id);

    }

    public List<Product> searchProducts(String keyword)
    {
        return productRepository.findByNameStartingWithIgnoreCase(keyword);
    }

    public Product updateProduct(Long id,Product product,User user)
    {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı id: " + id));

        if (!existingProduct.getAdminId().equals(user.getId())) {
            throw new RuntimeException("Yetkisiz İşlem: Sadece kendi eklediğiniz ürünleri güncelleyebilirsiniz!");
        }


        if (product.getName() != null){
            existingProduct.setName(product.getName());
        }

        if (product.getStock() != null)
        {
            existingProduct.setStock(product.getStock());
        }

        if (product.getPrice() != null)
        {
            existingProduct.setPrice(product.getPrice());
        }

        if (product.getDescription() != null)
        {
            existingProduct.setDescription(product.getDescription());
        }

        if (product.getImageUrl() != null)
        {
            existingProduct.setImageUrl(product.getImageUrl());
        }

        if (product.getCategory() != null)
        {
            existingProduct.setCategory(product.getCategory());
        }

        return productRepository.save(existingProduct);
    }

    public ProductDTO updatedProductWithImage(Long id, MultipartFile imageFile,String productDataJson,User user) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productDataJson, ProductDTO.class);

        if (imageFile != null && !imageFile.isEmpty())
        {
            String savedFileName = fileService.saveImages(imageFile);
            productDTO.setImageUrl(savedFileName);
        }

        Product product = productMapper.toEntity(productDTO);
        product.setId(id);

        Product updateProduct = this.updateProduct(id,product,user);

        return productMapper.toDto(updateProduct);
    }

    // YENİ EKLENEN METOT: Giriş yapan satıcının (admin) kendi ürünlerini getirir
    public Page<ProductDTO> getMyProducts(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> myProductPage = productRepository.findByAdminId(user.getId(), pageable);
        return myProductPage.map(productMapper::toDto);
    }

}
