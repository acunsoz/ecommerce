package com.acunsoz.ecommerce_backend.service;

import com.acunsoz.ecommerce_backend.exception.ProductNotFoundException;
import com.acunsoz.ecommerce_backend.model.entity.Product;
import com.acunsoz.ecommerce_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product saveProduct(Product product)
    {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts()
    {
     return productRepository.findAll();
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
