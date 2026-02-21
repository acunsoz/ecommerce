package com.acunsoz.ecommerce_backend.controller;

import com.acunsoz.ecommerce_backend.model.dto.ProductDTO;
import com.acunsoz.ecommerce_backend.model.entity.Product;
import com.acunsoz.ecommerce_backend.model.mapper.ProductMapper;
import com.acunsoz.ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO)
    {
        Product product = productMapper.toEntity(productDTO);

        Product savedProduct = productService.saveProduct(product);

        return productMapper.toDTO(savedProduct);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts()
    {
        List<Product> productsAll = productService.getAllProducts();

        return productsAll.stream()
                .map(productMapper::toDTO)
                .toList();

    }

    @DeleteMapping("/{id}")
    public void deleteProduct (@PathVariable Long id)
    {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id ,@RequestBody ProductDTO productDTO)
    {
        Product product = productMapper.toEntity(productDTO);
        product.setId(id);
        Product updatedProduct = productService.updateProduct(id,product);

        return productMapper.toDTO(updatedProduct);

    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(@RequestParam("name") String keyword)
    {
        List<Product> searchList = productService.searchProducts(keyword);
        return searchList.stream()
                .map(productMapper::toDTO)
                .toList();
    }


}
