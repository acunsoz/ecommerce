package com.acunsoz.ecommerce_backend.controller;

import com.acunsoz.ecommerce_backend.model.dto.ProductDTO;
import com.acunsoz.ecommerce_backend.model.entity.Product;
import com.acunsoz.ecommerce_backend.model.entity.User;
import com.acunsoz.ecommerce_backend.model.mapper.IProductMapper;
import com.acunsoz.ecommerce_backend.service.FileService;
import com.acunsoz.ecommerce_backend.service.ProductService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final IProductMapper productMapper;
    private final FileService fileService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProductDTO> createProduct(
            @AuthenticationPrincipal User user,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestPart("productData") String productDataJson) throws IOException
    {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productDataJson, ProductDTO.class);

        if (imageFile != null && !imageFile.isEmpty()){

            String savedFileName = fileService.saveImages(imageFile);

            productDTO.setImageUrl(savedFileName);

        }

        ProductDTO savedProduct = productService.saveProduct(productDTO,user);

        return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);

    }

    @GetMapping
    public Page<ProductDTO> getAllProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    )
    {
        return productService.getAllProducts(categoryId,page,size);

    }

    @DeleteMapping("/{id}")
    public void deleteProduct (
            @AuthenticationPrincipal User user,
            @PathVariable Long id)
    {
        productService.deleteProduct(id,user);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(
            @PathVariable Long id ,
            @AuthenticationPrincipal User user,
            @RequestBody ProductDTO productDTO)
    {
        Product product = productMapper.toEntity(productDTO);
        product.setId(id);
        Product updatedProduct = productService.updateProduct(id,product,user);

        return productMapper.toDto(updatedProduct);

    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(@RequestParam("name") String keyword)
    {
        List<Product> searchList = productService.searchProducts(keyword);
        return searchList.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @PutMapping(value = "/{id}",consumes = "multipart/form-data")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestPart("productData") String productDataJson) throws IOException
    {
        ProductDTO updatedProduct = productService.updatedProductWithImage(id,imageFile,productDataJson,user);

        return ResponseEntity.ok(updatedProduct);

    }

    @GetMapping("/my-products")
    public ResponseEntity<Page<ProductDTO>> getMyProducts(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductDTO> myProducts = productService.getMyProducts(user, page, size);
        return ResponseEntity.ok(myProducts);
    }


}
