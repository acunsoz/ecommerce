package com.acunsoz.ecommerce_backend.service;

import com.acunsoz.ecommerce_backend.exception.ProductNotFoundException;
import com.acunsoz.ecommerce_backend.model.dto.ProductDTO;
import com.acunsoz.ecommerce_backend.model.entity.Category;
import com.acunsoz.ecommerce_backend.model.entity.Product;
import com.acunsoz.ecommerce_backend.model.mapper.IProductMapper;
import com.acunsoz.ecommerce_backend.repository.CategoryRepository;
import com.acunsoz.ecommerce_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final IProductMapper productMapper;
    private final CategoryRepository categoryRepository;


    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO)
    {
        // 1. Garsonun getirdiği sipariş fişini (DTO), mutfaktaki çiğ ete (Entity) çeviriyoruz
        Product product = productMapper.toEntity(productDTO);

        // 2. Depoya inip kullanıcının gönderdiği ID'ye (örneğin 1) sahip GERÇEK kategoriyi buluyoruz
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Bu id ye ait bir kategori bulunamadı! "));

        // 3. Bulduğumuz bu gerçek kategoriyi, ürünümüze (çiğ ete) sağlamca bağlıyoruz
        product.setCategory(category);

        // 4. Ürünü fırına veriyoruz (Veritabanına güvenle kaydediyoruz)
        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    public Page<ProductDTO> getAllProducts(int page, int size)
    {
        Pageable pageable = (Pageable) PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(product -> productMapper.toDto(product));


    }

    public void deleteProduct(Long id)
    {
        if (productRepository.existsById(id))
        {
            productRepository.deleteById(id);
        }
        else
        {
            throw new ProductNotFoundException("Silinecek ürün bulunamadı id: " + id);
        }

    }

    public List<Product> searchProducts(String keyword)
    {
        return productRepository.findByNameStartingWithIgnoreCase(keyword);
    }

    public Product updateProduct(Long id,Product product)
    {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı id: " + id));


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

        if (product.getCategory() != null)
        {
            existingProduct.setCategory(product.getCategory());
        }

        return productRepository.save(existingProduct);
    }

}
